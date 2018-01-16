package com.yusic.server.controller.websocket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class MessageController {
    @Autowired
    WebSocketMessageBrokerStats wm;

    @MessageMapping("/hello")
    @SendTo("/topic/hi")
    public String mmm (
            String msg) throws Exception{
        Gson g = new Gson();
        Map<String, String> tt = g.fromJson(msg, Map.class);
        String message = tt.get("msg");
        Map map = new HashMap();
        map.put("message", message);
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @MessageMapping("/init")
    @SendTo("/topic/total")
    public String getTotal () throws Exception{
        Gson g = new Gson();
        Map map = new HashMap();
        String[] argc = wm.getWebSocketSessionStatsInfo().toString().split(" ");
        String count = argc[0] == null ? "0" : argc[0];
        map.put("total", count);
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
