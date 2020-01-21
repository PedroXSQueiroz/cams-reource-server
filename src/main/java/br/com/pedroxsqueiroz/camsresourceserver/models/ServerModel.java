package br.com.pedroxsqueiroz.camsresourceserver.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;

@Table(name = "server")
@Entity
public class ServerModel extends NodeModel{
	
	public ServerModel() {
		
	}
	
	public ServerModel(NodeConfig config) {
		super(config);
	}
	
}
