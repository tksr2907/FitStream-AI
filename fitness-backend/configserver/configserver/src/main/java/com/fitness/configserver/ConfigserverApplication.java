package com.fitness.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}
//A Config Server is a central place where all your app configurations are stored and managed, instead of keeping configs inside each service.
//An API Gateway is like a middleman that:
//Receives requests from clients (frontend/mobile)
//Routes them to the correct microservice
//Returns the response back to the client
//Without API Gateway:
//Client has to call multiple services directly
//Needs to know:Ports,Service names,URLs
//With API Gateway:
//Client calls only one URL
//Gateway handles everything
//✅ Clean + scalable
