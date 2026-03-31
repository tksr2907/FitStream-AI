package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data//it generate common code like getters,setters,constructor,etc.
public class ActivityRequest {

    private String userId;//user id
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String,Object> additionalMetrics;
}
