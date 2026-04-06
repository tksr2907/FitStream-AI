package com.fitness.aiservice.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {
    //WebClient is a non-blocking, asynchronous HTTP client in Spring WebFlux used to call external APIs or services.
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiurl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;
//WebClient.Builder is used to configure and create a WebClient
//Spring gives me a builder->I use it to create WebClient->I keep it to use later for API calls
    public GeminiService(WebClient.Builder webClientBuilder){
        this.webClient=webClientBuilder.build();
    }

    public String getRecommendations(String details){
        Map<String,Object> requestBody=Map.of(
                "contents",new Object[]{
                     Map.of("parts",new Object[]{
                         Map.of("text",details)
                })
                }
        );
        String response=webClient.post()
                .uri(geminiApiurl)
                .header("Content-Type","application/json")
                .header("x-goog-api-key",geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;

    }


}
