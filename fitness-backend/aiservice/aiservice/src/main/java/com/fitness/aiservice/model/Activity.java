package com.fitness.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;


@Data//this gives all getters and setters.
@Builder//it is lombok annotation that help us to creage object easily and clealy without wiriting constructor or setter manually.
@AllArgsConstructor//constructor with field as paramaters,create object +set all values in one strep
@NoArgsConstructor//constructor with no parameters first create empty object then assign values
public class Activity {
    private String id;//activity id
    private String userId;//user id
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @Field("metrics")//in java->variable name is additional metrics and in mongodb it will stored as metrics.
    private Map<String,Object> additionalMetrics;

    @CreatedDate//automatically stores when the document was created.
    private LocalDateTime createdAt;
    @LastModifiedDate//automatically updates when the document is modified.
    private LocalDateTime updatedAt;
}
