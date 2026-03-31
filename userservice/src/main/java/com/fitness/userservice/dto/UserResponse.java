package com.fitness.userservice.dto;
import lombok.Data;
import java.time.LocalDateTime;
//userresponse sends data back to client.
@Data//we get all getter ,setter and constructor.
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
