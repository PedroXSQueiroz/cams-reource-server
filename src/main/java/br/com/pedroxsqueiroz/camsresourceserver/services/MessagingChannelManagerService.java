package br.com.pedroxsqueiroz.camsresourceserver.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.exceptions.ServerNotRegisteredException;
import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;

@Service
@Scope("singleton")
public class MessagingChannelManagerService {
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private NodeConfig thisNodeConfig;
	
	private Map<String, Connection> connections = new HashMap<String, Connection>();
	private Map<String, Channel> channels = new HashMap<String,Channel>();
	
	
	private Connection getConnection(String nodeKey) throws ServerNotRegisteredException, IOException, TimeoutException 
	{
		
		NodeModel server = this.thisNodeConfig.getKey().equals(nodeKey) ? this.thisNodeConfig : this.serverService.findByKey(nodeKey) ;
		
		return this.getConnection(server);
	}
	
	private Connection getConnection(NodeModel node) throws IOException, TimeoutException 
	{
		String nodeKey = node.getKey();
		
		if(!connections.containsKey(nodeKey) || !connections.get(nodeKey).isOpen()) 
		{
			
			ConnectionFactory messagingServerConnectionFactory = new ConnectionFactory();
			messagingServerConnectionFactory.setHost(node.getMessagingBrokerAddress());
			messagingServerConnectionFactory.setPort(node.getMessagingBrokerPort());
			
			Connection newConnection = messagingServerConnectionFactory.newConnection();
			
			this.connections.put(nodeKey, newConnection);
		}
		
		return connections.get(nodeKey);
	}
	
	public Channel getChannel(NodeModel node) throws IOException, ServerNotRegisteredException, TimeoutException 
	{
		
		String nodeKey = node.getKey();
		
		return this.getChannel(nodeKey);
	
	}
	
	public Channel getChannel(String nodeKey) throws IOException, ServerNotRegisteredException, TimeoutException
	{
		if(!channels.containsKey(nodeKey) || !channels.get(nodeKey).isOpen()) 
		{
			Channel channel = this.getConnection(nodeKey).createChannel();
			this.channels.put(nodeKey, channel);
		}
		
		return channels.get(nodeKey);
	}
	
}
