package br.com.pedroxsqueiroz.camsresourceserver.services;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.camsresourceserver.exceptions.ServerNotRegisteredException;
import br.com.pedroxsqueiroz.camsresourceserver.models.CamModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.ClientModel;

@Service
public class ClientService extends AbstractNodeService<ClientModel>{

	@Autowired
	private MessagingService messagingService;
	
	public List<CamModel> allClientCams() {
		// TODO Auto-generated method stub
		
		List<CamModel> cams = this.list().stream().flatMap(client -> {
		
			try 
			{
				CamModel[] clientCams = this.messagingService.send(client, "list-cams", new byte[0], CamModel[].class);
				
				return Stream.of(clientCams);
			
			} catch (IOException | InterruptedException | ServerNotRegisteredException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		
		}).collect(Collectors.toList());
		
		return cams;
		
	}
	
}
