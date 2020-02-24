package br.com.pedroxsqueiroz.camsresourceserver.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.pedroxsqueiroz.camsresourceserver.models.CamModel;
import br.com.pedroxsqueiroz.camsresourceserver.models.ClientModel;
import br.com.pedroxsqueiroz.camsresourceserver.services.ClientService;

@Controller
@RequestMapping(value = "clients")
@CrossOrigin("*")
public class ClientController extends AbstractNodeController<ClientModel>{
	
	@GetMapping(value = "cams")
	@ResponseBody
	public List<CamModel> allClientCams()
	{
		
		ClientService clientService = (ClientService) this.service;
		
		return clientService.allClientCams();
		
	}
	
}
