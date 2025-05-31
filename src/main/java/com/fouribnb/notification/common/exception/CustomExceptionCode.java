package com.fouribnb.notification.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionCode implements ExceptionCode {

    SLACK_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "슬랙 메시지 전송에 실패했습니다."),
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저에 보낼 알림이 없습니다."),
    SCHEDULER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"알림 발송 스케줄러 오류 발생했습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
