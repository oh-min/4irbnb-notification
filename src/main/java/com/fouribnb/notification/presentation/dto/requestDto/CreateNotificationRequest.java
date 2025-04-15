package com.fouribnb.notification.presentation.dto.requestDto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateNotificationRequest(
    UUID reservationId,
    Long userId,
    UUID lodgeId,
    LocalDateTime checkInDate,
    LocalDateTime checkOutDate,
    String reservationStatus
) {

}
