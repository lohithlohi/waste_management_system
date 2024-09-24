package com.ust.wastewarden.bin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableScheduling
public class BinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinApplication.class, args);
	}

}
