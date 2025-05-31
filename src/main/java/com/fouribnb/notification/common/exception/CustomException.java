package com.fouribnb.notification.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public CustomException(ExceptionCode e) {
        super(e.getMessage());
        this.exceptionCode = e;
    }

    public CustomException(ExceptionCode e, Exception exception) {
        super(e.getMessage(),exception);
        this.exceptionCode = e;
    }
}
