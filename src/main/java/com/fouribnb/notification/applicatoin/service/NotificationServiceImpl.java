package com.fouribnb.notification.applicatoin.service;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.applicatoin.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.applicatoin.mapper.NotificationMapper;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import com.fouribnb.notification.infrastructure.jpa.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

        private final NotificationRepository notificationRepository;
//    private final NotificationJpaRepository notificationJpaRepository;

    public NotificationInternalResponse createNotification(CreateNotificationInternalRequest request) {
        // 예약 정보를 가져와서  -> 알림 메세지로 조합
        Notification notification = NotificationMapper.toEntity(request);
        log.info("mapper 적용 후 : {}", notification.toString());
        // DB에 저장
        Notification saved = notificationRepository.save(notification);
//        Notification saved = notificationJpaRepository.save(notification);
        log.info("DB 저장 : {}", saved);
        return NotificationMapper.toResponse(saved);
    }
}
