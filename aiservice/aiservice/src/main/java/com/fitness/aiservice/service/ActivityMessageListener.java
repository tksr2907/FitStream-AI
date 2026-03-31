package com.fitness.aiservice.service;


import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepository;


//6.this method is automatically triggered when a message arrives.kafka pushes message to listener.
    @KafkaListener(topics = "${kafka.topic.name}",groupId = "activity-processor-group")
    public void processActivity(Activity activity){
        log.info("Received Activity for processing: {}", activity.getUserId());
      //7.Here activityAIService is called
        Recommendation recommendation=  activityAIService.generateRecommendation(activity);
      recommendationRepository.save(recommendation);
    }


}
//@Slf4j is a Lombok annotation that automatically creates a logger object for your class.
//log.info()	General info
//log.debug()	Debugging
//log.error()	Errors
//log.warn()	Warnings