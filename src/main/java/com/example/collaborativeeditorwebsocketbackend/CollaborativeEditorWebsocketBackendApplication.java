package com.example.collaborativeeditorwebsocketbackend;

import com.example.collaborativeeditorwebsocketbackend.entity.SharedFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollaborativeEditorWebsocketBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborativeEditorWebsocketBackendApplication.class, args);
		SharedFile.init();
	}

}
