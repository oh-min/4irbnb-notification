package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import java.util.List;

public interface NotificationService {

    NotificationInternalResponse addNotification(CreateNotificationInternalRequest request);

    void autoSendNotificationsByScheduler();
}
