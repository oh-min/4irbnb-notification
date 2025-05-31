package com.fouribnb.notification.domain.repository;

import com.fouribnb.notification.domain.entity.Notification;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository {

    Notification save(Notification notification);

    List<Notification> findByIsSuccessFalse();

    List<Notification> findByUserIdAndIsSuccessFalse(Long userId);

    Notification findByReservationId(UUID reservationId);

}
