package com.fouribnb.notification.infrastructure.channel;

import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.service.ChannelService;
import com.fouribnb.notification.domain.entity.Notification;
import com.fouribnb.notification.domain.repository.NotificationRepository;
import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class SlackServiceImpl implements ChannelService {

    private final NotificationRepository notificationRepository;

    @Value(value = "${slack.bot-token}")
    private String token;

    @Override
    @Transactional
    public List<ChannelResponse> sendSlack(List<ChannelRequest> requests) {

        MethodsClient methods = Slack.getInstance().methods(token);

        List<ChannelResponse> updatedNotifications = new ArrayList<>();

        for (ChannelRequest channelRequest : requests) {
            String channel = channelRequest.slackId();
            log.info("메세지 전송을 위한 데이터 : {}", requests);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(channelRequest.text())
                .build();
            try {
                methods.chatPostMessage(request);

                // 슬랙 전송이 성공 -> isSuccess 를 true 로 변경
                Notification notification = notificationRepository.findByReservationId(
                    channelRequest.reservationId());

                notification.setIsSuccess(true);

                updatedNotifications.add(ChannelMapper.toResponse(notification));
            } catch (Exception e) {
                throw new RuntimeException("Failed to send message to Slack", e);
            }
        }
        return updatedNotifications;
    }


}
