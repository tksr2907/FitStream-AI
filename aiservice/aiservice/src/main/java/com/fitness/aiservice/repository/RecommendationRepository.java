package com.fitness.aiservice.repository;

import com.fitness.aiservice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation,String> {
    //Spring converts this into Mongo query->MongoDB returns all matching documents like recommendation,activity id.
    List<Recommendation> findByUserId(String userId);

  Optional <Recommendation> findByActivityId(String activityId);
}
