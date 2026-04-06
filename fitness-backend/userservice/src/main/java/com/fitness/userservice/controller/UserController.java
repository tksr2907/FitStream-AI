package com.fitness.userservice.controller;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;//i want to use UserService inside this class so i created varibale
//it does not create object it only creates reference variable
@GetMapping("/{userId}")
//@PathVariable in Spring Boot is used to get values from the URL path and pass them into your method.
public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
return ResponseEntity.ok(userService.getUserProfile(userId));


}
@GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.existByUserId(userId));
    }


    @PostMapping("/register")
//function name is "register"
//UserResponse → actual data + full HTTP response (status + body) + ResponseEntity → envelope
//RegisterRequest is a class, and request is a reference variable (which holds the object).
//Call the userservice to register the user and send the result back with success (200 OK).
//UserResponse is  a class that defines what you will return.
//Register is a method inside userservice
   public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
    return ResponseEntity.ok(userService.register(request));
}
}


//Flow-:
//client sends data(postman)->controller receive request->
//RegisterRequest->take input data from user(JSON)->store it in object form ->pass to service layer.






