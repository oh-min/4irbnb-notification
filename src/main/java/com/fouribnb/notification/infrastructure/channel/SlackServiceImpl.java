package com.fouribnb.notification.infrastructure.channel;

import com.fouribnb.notification.application.mapper.ChannelMapper;
import com.fouribnb.notification.application.service.ChannelService;
import com.fouribnb.notification.common.exception.CustomException;
import com.fouribnb.notification.common.exception.CustomExceptionCode;
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
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channel)
                .text(channelRequest.text())
                .build();
            try {
                methods.chatPostMessage(request);

                Notification notificationToUpdate = notificationRepository.findByReservationId(
                    channelRequest.reservationId());

                notificationToUpdate.setIsSuccess(true);

                updatedNotifications.add(ChannelMapper.toResponse(notificationToUpdate));
            } catch (Exception e) {
                throw new CustomException(CustomExceptionCode.SLACK_SEND_FAILED, e);
            }
        }
        return updatedNotifications;
    }


}
