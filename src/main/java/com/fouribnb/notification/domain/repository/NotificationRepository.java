package com.fouribnb.notification.domain.repository;

import com.fouribnb.notification.domain.entity.Notification;

public interface NotificationRepository {

    Notification save(Notification notification);
}
