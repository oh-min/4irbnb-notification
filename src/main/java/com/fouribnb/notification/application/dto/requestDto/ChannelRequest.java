package com.fouribnb.notification.application.dto.requestDto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ChannelRequest(
    UUID reservationId,
    String email,
    String slackId,
    String text
) {

}
