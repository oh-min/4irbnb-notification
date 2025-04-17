package com.fouribnb.notification.application.service;

import com.fouribnb.notification.application.dto.requestDto.ChannelRequest;
import com.fouribnb.notification.application.dto.responseDto.ChannelResponse;
import java.util.List;

public interface ChannelService {

    List<ChannelResponse> sendSlack(List<ChannelRequest> requests);
}
