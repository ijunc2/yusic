package com.yusic.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
@Slf4j
public class WebSocketSessionConnected implements
        ApplicationListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {

        log.info("connected :: {}", event.getUser());
    }
}