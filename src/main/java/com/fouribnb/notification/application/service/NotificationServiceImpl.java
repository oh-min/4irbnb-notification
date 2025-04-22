package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.mapper.NotificationMapper;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import com.fouribnb.notification.infrastructure.client.UserClient;
import com.fouribnb.notification.infrastructure.client.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @Override
    @Transactional
    public NotificationInternalResponse createNotification(
        CreateNotificationInternalRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        Notification saved = notificationRepository.save(notification);
        log.info("DB 저장 : {}", saved);
        return NotificationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public List<NotificationInternalResponse> sendNotification(Long userId) {
        // 유저 서비스에서 유저 정보(email, slackId) 받아오기
        ResponseEntity<UserResponse> userResponse = userClient.getUser(userId);

        String username = userResponse.getBody().username();
        String slackId = "@"+userResponse.getBody().slackId();
        String email = userResponse.getBody().email();

        log.info("가져온 유저 네임 : {}", username);
        log.info("가져온 유저 슬랙 아이디 : {}", slackId);

        // 유저 아이디로 알림 정보 가져오기 (isSuccess=false)
        List<Notification> findByUserId = notificationRepository.findByUserIdAndIsSuccess(userId,
            false);

        log.info("로그인된 유저의 알림 정보 : {}", findByUserId);
        if(findByUserId.isEmpty()) {
            throw new NullPointerException("해당 유저에 보낼 알림이 없습니다.");
        }

        // userId, email, slackId ->  Mapper -> ChannelRequest
        List<ChannelRequest> requests = new ArrayList<>();
        for (Notification notification : findByUserId) {

            String message = username + notification.getMessage();
            UUID reservationId = notification.getReservationId();

            requests.add(
                ChannelMapper.toChannelRequest(slackId, email, message, reservationId));
        }
        // 채널 서비스 인터페이스에 연결
        List<ChannelResponse> sent = channelService.sendSlack(requests);

        return NotificationMapper.toResponseList(sent);
    }
}
