package com.fouribnb.notification.applicatoin.mapper;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.applicatoin.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.enums.Type;

public class NotificationMapper {

    // 내부 Dto -> Entity
    public static Notification toEntity(CreateNotificationInternalRequest request) {

        String title =
            request.userId() + "님의 예약하신 숙소가 " + request.reservationStatus() + "로 변경되었습니다.";
        String message =
            "숙소 : " + request.lodgeId() + ", 체크인 : " + request.checkInDate() + ", 체크아웃 : "
                + request.checkOutDate();

        Notification notification = Notification.builder()
            .userId(request.userId())
            .title(title)
            .message(message)
            .type(Type.RESERVATION)
            .build();
        return notification;
    }

    // Entity -> 내부 Dto
    public static NotificationInternalResponse toResponse(Notification notification) {
        NotificationInternalResponse internalResponse = NotificationInternalResponse.builder()
            .notificationId(notification.getNotificationId())
            .userId(notification.getUserId())
            .title(notification.getTitle())
            .message(notification.getMessage())
            .type(notification.getType())
            .isSuccess(notification.isSuccess())
            .build();
        return internalResponse;
    }

}
