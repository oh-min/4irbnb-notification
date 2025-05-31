package com.fouribnb.notification.infrastructure.jpa;

import com.fouribnb.notification.domain.entity.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByIsSuccessFalse();

    List<Notification> findByUserIdAndIsSuccessFalse(Long userId);

    Notification findByReservationId(UUID reservationId);

}
