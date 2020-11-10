package com.example.webchat.services;

import com.example.webchat.dto.ChatMessage;
import com.example.webchat.dto.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    public ChatService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.chatRooms = new LinkedHashMap<>();
    }

    public ArrayList<ChatRoom> findAllChatRoom(){
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findChatRoomById(String id){
        return chatRooms.get(id);
    }

    public ChatRoom createChatRoom(String name){
        String id = UUID.randomUUID().toString();
        ChatRoom newChatRoom = ChatRoom.of(id, name);
        chatRooms.put(id, newChatRoom);
        return newChatRoom;
    }

    public void sendMessage(WebSocketSession webSocketSession, ChatMessage chatMessage){
        try{
            webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }catch (Exception e){
            log.error("sendMessage", e);
        }
    }

}
