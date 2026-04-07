package com.fitness.activityservice.service;


import com.fitness.activityservice.repository.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    //KafkaTemplate is a class provided by Spring Kafka that helps you send messages to Apache Kafka easily.
    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) {
    //1.check valid user id or not.
       boolean isValidUser = userValidationService.validateUser(request.getUserId());
       if(!isValidUser){
           throw new RuntimeException("Invalid user:"+ request.getUserId());
       }

       //2.you are converting request->entity(DB object)
        Activity activity= Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

       //3.save to database.
        Activity savedActivity= null;

            savedActivity = activityRepository.save(activity);

            //4.send event to kafka(Kafka stores this message in a topic)
            //kafka stores message be like-:
            //Topic: activity-topic
            //Message:
            //Key: user123,Value: { activity details }
        try {
            kafkaTemplate.send(topicName,savedActivity.getUserId(),savedActivity);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response=new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;



    }

    public List<ActivityResponse> getUserActivities(String userId) {
    List<Activity> activityList =activityRepository.findByUserId(userId);
        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
