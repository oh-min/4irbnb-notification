package com.fouribnb.notification.presentation.dto.responseDto;

import com.fouribnb.notification.domain.enums.Type;
import java.util.UUID;
import lombok.Builder;

@Builder
public record NotificationResponse(
    UUID notificationId,
    Long userId,
    String title,
    String message,
    String type,
    boolean isSuccess
) {

}
