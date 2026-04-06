package com.fitness.activityservice.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean//@Bean tells Spring: “Create this object and manage it for me.”
    @LoadBalanced//it allow us calling service using service name insteda of url
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://USER-SERVICE")
                .build();
    }
}
//You want to call an API (another service).To do that, you need a WebClient
//But creating WebClient again and again is annoying so we use WebClient.Builder
//WebClient is a tool in Spring Boot used to call APIs (send HTTP requests to another service).
//WebClient.Builder = a tool used to CREATE WebClient objects