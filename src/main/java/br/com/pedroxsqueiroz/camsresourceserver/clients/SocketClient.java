package br.com.pedroxsqueiroz.camsresourceserver.clients;

import org.springframework.web.socket.sockjs.client.SockJsClient;

public class SocketClient {

	private static SockJsClient socketClient;
	
	private SockJsClient getSocketClient() 
	{
		if(socketClient == null) 
		{
			
		}
		
		return socketClient;
	}
	
}
