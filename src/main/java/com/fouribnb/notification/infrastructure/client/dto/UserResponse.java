package com.fouribnb.notification.infrastructure.client.dto;

public record UserResponse(
    Long id,
    String email,
    String username,
    String nickname,
    String phone,
    String slackId
) {

}
