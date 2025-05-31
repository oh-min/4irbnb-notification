package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.mapper.NotificationMapper;
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
    public NotificationInternalResponse addNotification(
        CreateNotificationInternalRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        Notification saved = notificationRepository.save(notification);
        log.info("DB 저장 : {}", saved);
        return NotificationMapper.toResponse(saved);
    }

    // [자동 알림 발송]
    @Override
    @Transactional
    public void autoSendNotificationsByScheduler() {

        // DB 에서 is_success false 인 데이터 가져오기
        List<Notification> findByIsSuccess = notificationRepository.findByIsSuccess(false);

        List<Long> userIdList = new ArrayList<>();

        for (Notification notification : findByIsSuccess) {
            userIdList.add(notification.getUserId());
        }
        userIdList = userIdList.stream().distinct().collect(Collectors.toList());
        log.info("userId List : {}", userIdList);

        // 유저 서비스에서 해당 유저의 email, slackId 가져오기
        for (Long userId : userIdList) {
            ResponseEntity<UserResponse> userResponse = userClient.getUser(userId);

            String username = userResponse.getBody().username();
            String slackId = "@" + userResponse.getBody().slackId();
            String email = userResponse.getBody().email();

            log.info("가져온 유저 네임 : {}", username);
            log.info("가져온 유저 슬랙 아이디 : {}", slackId);
            log.info("가져온 유저 이메일 : {}", slackId);

            List<Notification> findByUserId = notificationRepository.findByUserIdAndIsSuccess(
                userId,
                false);

            log.info("로그인된 유저의 알림 정보 : {}", findByUserId);
            if (findByUserId.isEmpty()) {
                throw new NullPointerException("해당 유저에 보낼 알림이 없습니다.");
            }

            List<ChannelRequest> requests = new ArrayList<>();
            for (Notification notification : findByUserId) {

                String message = username + notification.getMessage();
                UUID reservationId = notification.getReservationId();

                requests.add(
                    ChannelMapper.toChannelRequest(slackId, email, message, reservationId));
            }

            // 채널 서비스 인터페이스에 연결
            List<ChannelResponse> sent = channelService.sendSlack(requests);

        }
    }

}
