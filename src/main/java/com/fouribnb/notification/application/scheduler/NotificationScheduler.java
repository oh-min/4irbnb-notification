package com.fouribnb.notification.application.scheduler;

import com.fouribnb.notification.application.service.NotificationService;
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
            log.error("알림 발송 스케줄러 오류 발생 : ", e);
        }

    }

}
