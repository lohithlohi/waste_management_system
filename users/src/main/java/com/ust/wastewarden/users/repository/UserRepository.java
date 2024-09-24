package com.ust.wastewarden.users.repository;

import com.ust.wastewarden.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

//     select * from patients where email = 'email'
//     @Query("select u from user u where u.email = :email")
    Optional<User> findByEmail(String email);


}
