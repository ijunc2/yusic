package com.yusic.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Slf4j
public class WebSocketSessionSubscribe implements
        ApplicationListener<SessionSubscribeEvent> {

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {

        log.info("Subscribe :: {}", event.getMessage());
    }
}