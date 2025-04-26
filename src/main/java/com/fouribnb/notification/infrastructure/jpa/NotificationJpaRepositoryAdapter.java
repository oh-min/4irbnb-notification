package com.fouribnb.notification.infrastructure.jpa;

import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationJpaRepositoryAdapter implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationJpaRepository.save(notification);
    }

    @Override
    public List<Notification> findByUserIdAndIsSuccess(Long userId, boolean isSuccess) {
        return notificationJpaRepository.findByUserIdAndIsSuccess(userId,false);
    }

    @Override
    public Notification findByReservationId(UUID reservationId) {
        return notificationJpaRepository.findByReservationId(reservationId);
    }

    @Override
    public List<Notification> findByIsSuccess(boolean isSuccess) {
        return notificationJpaRepository.findByIsSuccess(isSuccess);
    }
}