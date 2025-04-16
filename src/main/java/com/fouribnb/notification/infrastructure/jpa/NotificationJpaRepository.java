package com.fouribnb.notification.infrastructure.jpa;

import com.fouribnb.notification.domain.entity.Notification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, UUID> {

}
