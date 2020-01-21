package br.com.pedroxsqueiroz.camsresourceserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.daos.NodeDao;
import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;

public class AbstractNodeService<T extends NodeModel> {

	@Autowired
	private NodeDao<T> dao;
	
	public List<T> list()
	{
		return this.dao.findAll();
	}
	
	public List<T> list(Specification<T> spec)
	{
		return this.dao.findAll(spec);
	}
	
	public T get(Integer id) 
	{
		return this.dao.getOne(id);
	}
	
	public void delete(Integer id) 
	{
		this.dao.deleteById(id);
	}
	
	public T save(T node) 
	{
		this.dao.save(node);
		return node;
	}
	
	public NodeConfig getNodeConfig(String address) 
	{
		RestTemplate restTemplate = new RestTemplate();
		
		String nodeConfigurationUrl = String.format("%s/servers/self", address);
		
		ResponseEntity<NodeConfig> nodeConfigResponse = restTemplate.getForEntity(nodeConfigurationUrl, NodeConfig.class);
		
		NodeConfig nodeConfig = nodeConfigResponse.getBody();
		
		return nodeConfig;
	}
	
}
