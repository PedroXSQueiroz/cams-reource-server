package br.com.pedroxsqueiroz.camsresourceserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.daos.NodeDao;
import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;

public class AbstractService<T> {

	@Autowired
	protected JpaRepository<T, Integer> dao;
	
	public List<T> list()
	{
		return this.dao.findAll();
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
	
}
