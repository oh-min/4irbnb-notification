package com.fouribnb.notification.application.mapper;

import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.enums.Type;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import java.util.ArrayList;
import java.util.List;

public class NotificationMapper {

    // 내부 Dto -> Entity
    public static Notification toEntity(CreateNotificationInternalRequest request) {
        final String NOTIFICATION_MESSAGE_TEMPLATE = "님의 예약하신 숙소가 %s로 변경되었습니다.\n" +
            "예약 정보\n" +
            "숙소 : %s\n" +
            "체크인 : %s\n" +
            "체크아웃 : %s";

        String message = String.format(
            NOTIFICATION_MESSAGE_TEMPLATE,
            request.reservationStatus(),
            request.lodgeId(),
            request.checkInDate(),
            request.checkOutDate()
        );

        Notification notification = Notification.builder()
            .reservationId(request.reservationId())
            .userId(request.userId())
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
            .message(notification.getMessage())
            .type(notification.getType())
            .isSuccess(notification.isSuccess())
            .build();
        return internalResponse;
    }

    public static List<NotificationInternalResponse> toResponseList(
        List<ChannelResponse> notification) {
        List<NotificationInternalResponse> internalResponseList = new ArrayList<>();

        for (ChannelResponse ChannelResponseItem : notification) {

            NotificationInternalResponse internalResponse = NotificationInternalResponse.builder()
                .notificationId(ChannelResponseItem.notificationId())
                .userId(ChannelResponseItem.userId())
                .message(ChannelResponseItem.message())
                .type(ChannelResponseItem.type())
                .isSuccess(ChannelResponseItem.isSuccess())
                .build();

            internalResponseList.add(internalResponse);
        }

        return internalResponseList;
    }
}
