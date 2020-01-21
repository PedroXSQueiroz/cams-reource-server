package br.com.pedroxsqueiroz.camsresourceserver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.security.auth.kerberos.KeyTab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.pedroxsqueiroz.camsresourceserver.config.NodeConfig;

@SpringBootApplication
public class CamsResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamsResourceServerApplication.class, args);
	}
	
	@Value("${node.key_location}") 
	private String localKeyLocation;
	
	@Autowired 
	private NodeConfig localClientConfig;
	
	@PostConstruct
	public void loadLocalKey( ) throws IOException 
	{
		
		Path localKeyPath = Paths.get(this.localKeyLocation);
		
		byte[] keyBytes = Files.exists(localKeyPath) ? 
									this.readLocalKey(localKeyPath) :
									this.createLocalKey(localKeyPath);
		
		String key = new String(keyBytes, Charset.forName("UTF-8"));
		
		this.localClientConfig.setKey(key);
		
		
	}

	private byte[] readLocalKey(Path localKeyPath) throws IOException
	{		
		return Files.readAllBytes(localKeyPath);
	}
	
	private byte[] createLocalKey(Path localKeyPath) throws IOException {
		
		byte[] keyBuffer = new byte[256];
		new Random().nextBytes(keyBuffer);
		Files.createFile(localKeyPath);
		Files.write(localKeyPath, keyBuffer);
		
		return keyBuffer;
		
	}
	
}
