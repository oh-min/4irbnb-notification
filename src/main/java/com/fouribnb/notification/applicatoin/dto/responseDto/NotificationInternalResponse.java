package com.fouribnb.notification.applicatoin.dto.responseDto;

import com.fouribnb.notification.domain.enums.Type;
import java.util.UUID;
import lombok.Builder;

@Builder
public record NotificationInternalResponse(
    UUID notificationId,
    Long userId,
    String title,
    String message,
    Type type,
    boolean isSuccess

) {


}

