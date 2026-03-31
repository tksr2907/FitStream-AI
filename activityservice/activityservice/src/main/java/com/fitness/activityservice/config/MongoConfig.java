package com.fitness.activityservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
@Configuration//class contains configuratuion setting for spring
@EnableMongoAuditing//enables automatic tracking of time fields in mongoDB
public class MongoConfig {

}
