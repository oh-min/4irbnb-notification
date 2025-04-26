package com.fouribnb.notification.domain.repository;

import com.fouribnb.notification.domain.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository {

    Notification save(Notification notification);

    List<Notification> findByUserIdAndIsSuccess(Long userId,boolean isSuccess);

    Notification findByReservationId(UUID reservationId);

    List<Notification> findByIsSuccess(boolean b);
}
