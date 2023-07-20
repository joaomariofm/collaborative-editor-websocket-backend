package com.example.collaborativeeditorwebsocketbackend;

import com.example.collaborativeeditorwebsocketbackend.entity.SharedFile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollaborativeEditorWebsocketBackendApplication {

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(CollaborativeEditorWebsocketBackendApplication.class, args);
		System.out.println(InetAddress.getLocalHost());

		SharedFile.init();
	}

}
