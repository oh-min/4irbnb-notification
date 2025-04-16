package com.fouribnb.notification.presentation.controller;

import com.fouribnb.notification.applicatoin.dto.requestDto.CreateNotificationInternalRequest;
import com.fouribnb.notification.applicatoin.dto.responseDto.NotificationInternalResponse;
import com.fouribnb.notification.applicatoin.service.NotificationService;
import com.fouribnb.notification.presentation.dto.requestDto.CreateNotificationRequest;
import com.fouribnb.notification.presentation.dto.responseDto.NotificationResponse;
import com.fouribnb.notification.presentation.mapper.NotificationDtoMapper;
import com.fourirbnb.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/notifications")
@RequiredArgsConstructor
public class NotificationInternalController {

    private final NotificationService notificationService;

    @PostMapping
    public BaseResponse<NotificationResponse> createNotification(
        @RequestBody CreateNotificationRequest request) {
        CreateNotificationInternalRequest internalRequest = NotificationDtoMapper.toCreateInternalDto(
            request);
        NotificationInternalResponse internalResponse = notificationService.createNotification(
            internalRequest);

        return BaseResponse.SUCCESS(NotificationDtoMapper.toResponse(internalResponse), "알림 저장 성공");
    }
}
