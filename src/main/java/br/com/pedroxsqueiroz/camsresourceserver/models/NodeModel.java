package br.com.pedroxsqueiroz.camsresourceserver.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
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
	
	@Column(name = "node_key")
	private String key;
	
	public NodeModel() 
	{
		super();
	}
	
	public NodeModel(NodeConfig config) 
	{
		this.setAddress( config.getAddress() );
		this.setName( config.getName() );
		this.setKey( config.getKey() );
		
	}
	
}
