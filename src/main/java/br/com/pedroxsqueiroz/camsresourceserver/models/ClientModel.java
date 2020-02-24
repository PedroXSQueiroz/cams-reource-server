package br.com.pedroxsqueiroz.camsresourceserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;

@Table(name = "client")
@Entity
public class ClientModel extends NodeModel{

	public ClientModel()
	{
		super();
	}
	
	public ClientModel(NodeConfig config )
	{
		super(config);
	}
	
}
