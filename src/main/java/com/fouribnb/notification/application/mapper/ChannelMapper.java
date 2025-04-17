package com.fouribnb.notification.application.mapper;

import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import com.fouribnb.notification.domain.entity.Notification;
import java.util.UUID;


public class ChannelMapper {

    // 유저 서비스 -> channelDto

    public static ChannelRequest toChannelRequest(String slackId,
        String email, String message, UUID reservationId) {
        ChannelRequest request = ChannelRequest.builder()
            .reservationId(reservationId)
            .slackId(slackId)
            .email(email)
            .text(message)
            .build();
        return request;
    }

    // Entity -> Channle Dto
    public static ChannelResponse toResponse(Notification notification) {
        ChannelResponse channelResponse = ChannelResponse.builder()
            .notificationId(notification.getNotificationId())
            .userId(notification.getUserId())
            .message(notification.getMessage())
            .type(notification.getType())
            .isSuccess(notification.isSuccess())
            .build();
        return channelResponse;
    }
}
