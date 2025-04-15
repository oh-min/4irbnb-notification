package com.fouribnb.notification.presentation.mapper;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.presentation.dto.requestDto.CreateNotificationRequest;

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

}
