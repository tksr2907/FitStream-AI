package com.fitness.userservice;

import com.fitness.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//user repository help us save and fetch data from databases without writing SQL.
@Repository//this means this class is used to talk to database
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail(String email);
}
