package com.fouribnb.notification.applicatoin.service;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.applicatoin.dto.responseDto.NotificationInternalResponse;

public interface NotificationService {

    NotificationInternalResponse createNotification(CreateNotificationInternalRequest request);
}
