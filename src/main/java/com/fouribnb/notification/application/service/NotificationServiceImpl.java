package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.application.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.mapper.NotificationMapper;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelService channelService;

    public NotificationInternalResponse createNotification(
        CreateNotificationInternalRequest request) {
        Notification notification = NotificationMapper.toEntity(request);
        Notification saved = notificationRepository.save(notification);
        log.info("DB 저장 : {}", saved);
        return NotificationMapper.toResponse(saved);
    }

    public List<NotificationInternalResponse> sendNotification(Long userId) {
        // 유저 서비스에서 유저 정보(email, slackId) 받아오기
        String username = "임시유저"; // 임시 데이터
        String slackId = "@ohyemin96";
        String email = "sample1111@gmail.com";

        // 유저 아이디로 알림 정보 가져오기 (isSuccess=false)
        List<Notification> findByUserId = notificationRepository.findByUserIdAndIsSuccess(userId,
            false);
        log.info("로그인된 유저의 알림 정보 : {}", findByUserId);

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
