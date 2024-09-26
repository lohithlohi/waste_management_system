package com.ust.wastewarden.collectionLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CollectionLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectionLogApplication.class, args);
	}

}
