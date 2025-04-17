package com.fouribnb.notification.application.mapper;

import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.infrastructure.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.infrastructure.dto.responseDto.ChannelResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


public class ChannelMapper {

    // ??? -> channelDto (수정 필요)
    public static Collection<? extends ChannelRequest> toChannelRequest(String slackId,
        String email, String message, UUID reservationId) {
        ChannelRequest request = ChannelRequest.builder()
            .reservationId(reservationId)
            .slackId(slackId)
            .email(email)
            .text(message)
            .build();
        return Collections.singleton(request);
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
