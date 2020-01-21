package br.com.pedroxsqueiroz.camsresourceserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.models.CamModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.ClientModel;

@Service
public class CamService extends AbstractNodeService<CamModel>{

	@Autowired
	private NodeConfig thisClientConfig;
	
	@Override
	public CamModel save(CamModel cam) {
		
		cam.setClient( new ClientModel( this.thisClientConfig ) );
		
		return super.save(cam);
	}
	
}
