package com.example;

import com.example.entity.SharedFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
		SharedFile.init();
	}
}
