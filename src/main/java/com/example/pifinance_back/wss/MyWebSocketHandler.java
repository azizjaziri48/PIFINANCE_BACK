package com.example.pifinance_back.wss;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming WebSocket messages here
        String payload = message.getPayload();
        // Process and respond to the message as needed
        session.sendMessage(new TextMessage("Received: " + payload));
    }
}
