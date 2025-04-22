package com.fouribnb.notification.infrastructure.client;

import com.fouribnb.notification.infrastructure.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "${user.service.url}")
public interface UserClient {

    @GetMapping("/internal/users/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id);

}
