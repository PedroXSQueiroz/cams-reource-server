package br.com.pedroxsqueiroz.camsresourceserver.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.exceptions.ServerNotRegisteredException;
import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.ServerModel;

@Service
public class MessagingService {

	@Autowired
	private ServerService serverService;
	
	@Autowired
	private NodeConfig thisClientConfig;
	
	@Autowired
	private MessagingChannelManagerService channelManager;
	
	private Map<String, MessageCallback> callbacks = new HashMap<String, MessageCallback>();
	
	public static interface MessageCallback
	{
		byte[] execute(String messageTag, Delivery message);
	}
	
	public void on(final String action, MessageCallback callback) throws IOException, ServerNotRegisteredException, TimeoutException 
	{
		this.registerQueue(action, callback);
		this.callbacks.put(action, callback);
	}
	
	public void connectServers() 
	{
		this.serverService.list().forEach(this::connectServer);
	}
	
	public void connectServer(ServerModel server) 
	{
		this.callbacks.entrySet().forEach( callbackEntry -> {
			
			try {
				
				String action = callbackEntry.getKey();
				MessageCallback callback = callbackEntry.getValue();
				
				this.registerQueue(action, callback, server);
			
			} catch (IOException | ServerNotRegisteredException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void registerQueue( String action, MessageCallback callback )
			throws IOException, ServerNotRegisteredException, TimeoutException {
		
		this.serverService.list().forEach( server -> {
			
			try {
				
				this.registerQueue(action, callback, server);
			
			} catch (IOException | ServerNotRegisteredException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
	}

	private void registerQueue(String action, MessageCallback callback, ServerModel server)
			throws IOException, ServerNotRegisteredException, TimeoutException {
		
		Channel channel = this.channelManager.getChannel(server);
		
		String nodeKey = this.thisClientConfig.getKey();
		final String queueName = String.format("%s.%s", nodeKey, action);
		final String queueResponseName = String.format("%s.%s.response", nodeKey, action);
		
		channel.queueDeclare( queueName, false, false, false, null );
		channel.queueDeclare( queueResponseName, false, false, false, null );
		
		channel.basicConsume( 
				queueName , 
				true, 
				(tag, message) -> {
					
					byte[] result = callback.execute(tag, message);
					
					channel.basicPublish("", queueResponseName, null, result);
					
				}, 
				consumerTag -> {});
	}
	
	//FIXME: IMPLEMENTAR CORRELATION ID PARA VERIFICAÇÃO DA RESPOSTA CORRETA
	public <T> T send(NodeModel destinyNode, String action, Object data, final Class<T> responseType) throws IOException, InterruptedException, ServerNotRegisteredException, TimeoutException 
	{
		String serverKeyDestiny = destinyNode.getKey();
		
		String queueName = String.format("%s.%s", serverKeyDestiny, action);
		String queueResponseName = String.format("%s.response", queueName);
		
		Channel channel = this.channelManager.getChannel(this.thisClientConfig);
		
		ObjectMapper requestBodySerializer = new ObjectMapper();
		byte[] requestBody = requestBodySerializer.writeValueAsBytes(data);
		
		channel.basicPublish("", queueName, null, requestBody);
		
		ArrayBlockingQueue<T> awaiter = new ArrayBlockingQueue<T>(1);
		
		String consumer = channel.basicConsume(queueResponseName, true, (tag, delivery) -> {
			
			byte[] body = delivery.getBody();
			ObjectMapper responseBodySerializer = new ObjectMapper();
			T response = responseBodySerializer.readValue(body, responseType);
			
			awaiter.offer(response);
			
		}, consumerTag -> {});
		
		
		T response = awaiter.take();
		
		channel.basicCancel(consumer);
		
		return response;
		
		
	}
	
}
