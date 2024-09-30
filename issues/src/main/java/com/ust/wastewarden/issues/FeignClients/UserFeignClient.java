package com.ust.wastewarden.issues.FeignClients;

import com.ust.wastewarden.issues.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERS")
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);
}
