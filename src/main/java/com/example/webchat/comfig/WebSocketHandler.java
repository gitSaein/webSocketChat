package com.example.webchat.comfig;

import com.example.webchat.dto.ChatMessage;
import com.example.webchat.dto.ChatRoom;
import com.example.webchat.services.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@EnableWebSocket
@Configuration
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    public WebSocketHandler(ObjectMapper objectMapper, ChatService chatService) {
        this.objectMapper = objectMapper;
        this.chatService = chatService;
    }

    /**
     *
        @Description: 여러 client 가 전송한 메시지를 받아 처리해줄 handler
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String payload = message.getPayload();

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom chatRoom = chatService.findChatRoomById(chatMessage.getChatRoomId());
        chatRoom.handleAction(session, chatMessage, chatService);
    }
}
