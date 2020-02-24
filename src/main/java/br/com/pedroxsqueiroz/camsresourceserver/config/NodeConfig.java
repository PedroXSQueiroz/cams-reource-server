package br.com.pedroxsqueiroz.camsresourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;

@Component
public class NodeConfig extends NodeModel {
	
	@Value("${node.address}") 
	@Override
	public void setAddress(String address) 
	{
		super.setAddress(address);
	};
	
	@Value("${node.name}") 
	@Override
	public void setName(String name) 
	{
		super.setName(name);
	}
	
	@Value("${messaging_broker.address}") 
	@Override
	public void setMessagingBrokerAddress(String name) 
	{
		super.setMessagingBrokerAddress(name);
	}
	
	@Value("${messaging_broker.port}")
	@Override
	public void setMessagingBrokerPort(Integer messagingBrokerPort) {
		// TODO Auto-generated method stub
		super.setMessagingBrokerPort(messagingBrokerPort);
	}
}
