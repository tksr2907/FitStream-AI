//DTO is used to transfer data between layers.
package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
//register request is input used when user sends data to backend means when user calls api .
@Data
public class RegisterRequest {
    @NotBlank(message="Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message="password is required")
    @Size(min=6,message = "password must have atleast 6 characters")
    private String password;
    private String firstName;
    private String lastName;


}
