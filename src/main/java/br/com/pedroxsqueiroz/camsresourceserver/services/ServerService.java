package br.com.pedroxsqueiroz.camsresourceserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;
import br.com.pedroxsqueiroz.camsresourceserver.models.ClientModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.NodeModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.ServerModel;

@Service
public class ServerService extends AbstractNodeService<ServerModel>{
	
	@Autowired
	private NodeConfig thisClientConfig;
	
	public void registerClient(String serverAddress, ClientModel client) {
	
		RestTemplate restTemplate = new RestTemplate();
		
		String saveClientUrl = String.format("%s/clients/", serverAddress);
		
		
		ResponseEntity<ClientModel> saveClientResponse = restTemplate.postForEntity(saveClientUrl, client, ClientModel.class);
		
		//FIXME: VERIFICAR RESPOSTA E TRATAR ERROS
	}
	
	@Override
	public ServerModel save(ServerModel serverData) 
	{
		String serverAddress = serverData.getAddress();
		
		//SELF REGISTER AS CLIENT ON SERVER
		ClientModel thisAsClient = new ClientModel( this.thisClientConfig );
		this.registerClient( serverAddress, thisAsClient );
		
		
		NodeConfig serverConfig = this.getNodeConfig(serverAddress);
		ServerModel server = new ServerModel(serverConfig);
		
		return super.save(server);
	}

}
