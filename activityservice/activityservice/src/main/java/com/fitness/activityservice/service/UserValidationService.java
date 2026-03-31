package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    //userservice is injected here in activity service
   private final WebClient userServiceWebClient;

   public boolean validateUser(String userId){
       log.info("calling usr service for{}",userId);
       try {
           return userServiceWebClient.get()
                   .uri("/api/users/{userId}/validate",userId)
                   .retrieve()
                   .bodyToMono(Boolean.class)
                   .block();
       } catch (WebClientException e) {
           e.printStackTrace();
       }

       return false;
   }
}
//This class will call another microservice (User Service)
// It will send HTTP requests (GET/POST)
//It will receive user data as response
//It may validate or process that data
