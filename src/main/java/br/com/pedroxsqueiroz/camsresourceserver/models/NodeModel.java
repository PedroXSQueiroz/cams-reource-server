package br.com.pedroxsqueiroz.camsresourceserver.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.models.ServerModel.ServerModelBuilder;
import lombok.Builder;
import lombok.Data;

@Data	
@MappedSuperclass
public class NodeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "messaging_broker_address")
	private String messagingBrokerAddress;
	
	@Column(name = "messaging_broker_port")
	private Integer messagingBrokerPort;
	
	@Column(name = "node_key")
	private String key;
	
	public NodeModel() 
	{
		super();
	}
	
	public NodeModel(NodeConfig config) 
	{
		this.setName( config.getName() );
		this.setMessagingBrokerAddress( config.getMessagingBrokerAddress() );
		this.setMessagingBrokerPort( config.getMessagingBrokerPort() );
		this.setAddress( config.getAddress() );
		this.setKey( config.getKey() );
	}

	public NodeModel(Integer id, String name, String address, String messagingBrokerAddress,
			Integer messagingBrokerPort, String key) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.messagingBrokerAddress = messagingBrokerAddress;
		this.messagingBrokerPort = messagingBrokerPort;
		this.key = key;
	}
	
}
