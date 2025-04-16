package com.fouribnb.notification.presentation.mapper;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.applicatoin.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.presentation.dto.requestDto.CreateNotificationRequest;
import com.fouribnb.notification.presentation.dto.responseDto.NotificationResponse;

public class NotificationDtoMapper {

    // 외부 Dto -> 내부 Dto
    public static CreateNotificationInternalRequest toCreateInternalDto(
        CreateNotificationRequest request) {
        return new CreateNotificationInternalRequest(
            request.reservationId(),
            request.userId(),
            request.lodgeId(),
            request.checkInDate(),
            request.checkOutDate(),
            request.reservationStatus()
        );
    }

    // 내부 Dto -> 외부 Dto
    public static NotificationResponse toResponse(NotificationInternalResponse internalResponse) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
            .notificationId(internalResponse.notificationId())
            .userId(internalResponse.userId())
            .title(internalResponse.title())
            .message(internalResponse.message())
            .type(internalResponse.type())
            .isSuccess(internalResponse.isSuccess())
            .build();
        return notificationResponse;
    }
}
