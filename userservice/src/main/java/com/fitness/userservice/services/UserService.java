package com.fitness.userservice.services;

import com.fitness.userservice.UserRepository;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    //This function takes user data (RegisterRequest), processes it, and returns a result (UserResponse)
    //using dependency injection via autowired .spring automatically injects the repository bean into the services.
    private final UserRepository repository;
    public @Nullable UserResponse register(RegisterRequest request) {

        if(repository.existsByEmail(request.getEmail())){
            User existingUser = repository.findByEmail(request.getEmail());

            //this is waht you return to client
            UserResponse userResponse=new UserResponse();

            userResponse.setId(existingUser.getId());
            userResponse.setPassword(existingUser.getPassword());
            userResponse.setEmail(existingUser.getEmail());
            userResponse.setFirstName(existingUser.getFirstName());
            userResponse.setLastName(existingUser.getLastName());
            userResponse.setCreatedAt(existingUser.getCreatedAt());
            userResponse.setUpdatedAt(existingUser.getUpdatedAt());

            return userResponse;
        }



        User user=new User();//Empty object created
        //copy data from request to user means taking data from API request putting into user entity
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setKeycloakId(request.getKeycloakId());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        //saved data in DB
        User savedUser = repository.save(user);

        //this is waht you return to client
        UserResponse userResponse=new UserResponse();

        userResponse.setKeycloakId(savedUser.getKeycloakId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName((savedUser.getFirstName()));
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

    return userResponse;
    }

    public @Nullable UserResponse getUserProfile(String userId) {
    User user=repository.findById(userId)
            .orElseThrow(()-> new RuntimeException("user not found"));


        UserResponse userResponse=new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName((user.getFirstName()));
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        log.info("calling usr service for{}",userId);
       return repository.existsByKeycloakId(userId);

    }
}









//service layer work-:
//1.convert DTO->Entity
//2.save to database.
//3.create response DTO
//4.return response.
//All args constructor is a constructor that initializes all fields of a class at the time of object creation.
//public class User {
//
//    private String name;
//    private String email;
//
//    // All Args Constructor
//    public User(String name, String email) {
//        this.name = name;
//        this.email = email;
//Synchronization means controlling access to shared resources
// so that only one process/thread uses it at a time, preventing conflicts or incorrect results.

