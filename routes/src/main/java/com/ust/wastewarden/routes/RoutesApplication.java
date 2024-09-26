package com.ust.wastewarden.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RoutesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutesApplication.class, args);
	}

}
