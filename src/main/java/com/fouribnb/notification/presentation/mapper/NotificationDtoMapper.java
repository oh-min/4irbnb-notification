package com.fouribnb.notification.presentation.mapper;

import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.presentation.dto.requestDto.CreateNotificationRequest;
import com.fouribnb.notification.presentation.dto.responseDto.NotificationResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationDtoMapper {

    // 외부 Dto -> 내부 Dto
    public static CreateNotificationInternalRequest toCreateInternalDto(
        CreateNotificationRequest request) {
        CreateNotificationInternalRequest internalRequest = CreateNotificationInternalRequest.builder()

            .reservationId(request.reservationId())
            .userId(request.userId())
            .lodgeId(request.lodgeId())
            .checkInDate(request.checkInDate())
            .checkOutDate(request.checkOutDate())
            .reservationStatus(request.reservationStatus())
            .build();
        return internalRequest;
    }

    // 내부 Dto -> 외부 Dto
    public static NotificationResponse toResponse(NotificationInternalResponse internalResponse) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
            .notificationId(internalResponse.notificationId())
            .userId(internalResponse.userId())
            .message(internalResponse.message())
            .type(internalResponse.type().name())
            .isSuccess(internalResponse.isSuccess())
            .build();
        return notificationResponse;
    }

    public static List<NotificationResponse> toResponseList(
        List<NotificationInternalResponse> internalResponse) {
        List<NotificationResponse> responseList = new ArrayList<>();

        for (NotificationInternalResponse internalResponseItem : internalResponse) {

            NotificationResponse response = NotificationResponse.builder()
                .notificationId(internalResponseItem.notificationId())
                .userId(internalResponseItem.userId())
                .message(internalResponseItem.message())
                .type(internalResponseItem.type().name())
                .isSuccess(internalResponseItem.isSuccess())
                .build();

            responseList.add(response);
        }

        return responseList;
    }
}
