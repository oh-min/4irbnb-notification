package com.fouribnb.notification.application.scheduler;

import com.fouribnb.notification.application.service.NotificationService;
import com.fouribnb.notification.common.exception.CustomException;
import com.fouribnb.notification.common.exception.CustomExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 10000)
    public void scheduleTask() {

        try {
            notificationService.autoSendNotificationsByScheduler();
        } catch (Exception e) {
            throw new CustomException(CustomExceptionCode.SCHEDULER_FAILED,e);
        }

    }

}
