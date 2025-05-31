package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.mapper.NotificationMapper;
import com.fouribnb.notification.common.exception.CustomException;
import com.fouribnb.notification.common.exception.CustomExceptionCode;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import com.fouribnb.notification.infrastructure.client.UserClient;
import com.fouribnb.notification.infrastructure.client.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelService channelService;
    private final UserClient userClient;

    // [알림 생성]
    @Override
    @Transactional
    public NotificationInternalResponse addNotification(CreateNotificationInternalRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        Notification saved = notificationRepository.save(notification);
        return NotificationMapper.toResponse(saved);
    }

    // [자동 알림 발송]
    @Override
    @Transactional
    public void autoSendNotificationsByScheduler() {

        List<Notification> failedNotifications = notificationRepository.findByIsSuccessFalse();

        List<Long> userIds = new ArrayList<>();

        for (Notification notification : failedNotifications) {
            userIds.add(notification.getUserId());
        }
        userIds = userIds.stream().distinct().collect(Collectors.toList());

        for (Long userId : userIds) {
            ResponseEntity<UserResponse> user = userClient.getUser(userId);

            String username = user.getBody().username();
            String slackId = "@" + user.getBody().slackId();
            String email = user.getBody().email();

            List<Notification> pendingNotifications = notificationRepository.findByUserIdAndIsSuccessFalse(
                userId);

            if (pendingNotifications.isEmpty()) {
                throw new CustomException(CustomExceptionCode.NOTIFICATION_NOT_FOUND);
            }

            List<ChannelRequest> channelRequests = new ArrayList<>();
            for (Notification notification : pendingNotifications) {

                String message = username + notification.getMessage();
                UUID reservationId = notification.getReservationId();

                channelRequests.add(
                    ChannelMapper.toChannelRequest(slackId, email, message, reservationId));
            }

            channelService.sendSlack(channelRequests);

        }
    }

}
