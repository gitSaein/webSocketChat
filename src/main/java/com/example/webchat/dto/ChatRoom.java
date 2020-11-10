package com.example.webchat.dto;

import com.example.webchat.contants.MessageType;
import com.example.webchat.services.ChatService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String id;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom of(String id, String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = id;
        chatRoom.name = name;
        return chatRoom;
    }

    public void handleAction(WebSocketSession webSocketSession, ChatMessage chatMessage,
                             ChatService chatService){
        if(chatMessage.getMessageType().equals(MessageType.ENTER)){
            sessions.add(webSocketSession);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다. ");
        }
        else if(chatMessage.getMessageType().equals(MessageType.LEAVE)){
            sessions.remove(webSocketSession);
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장하셨습니다.");
        }else{
            chatMessage.setMessage(chatMessage.getSender() + " : " + chatMessage.getMessage());
        }

        sendMessage(chatMessage, chatService);

    }
    private void sendMessage(ChatMessage chatMessage, ChatService chatService){
        sessions
                .parallelStream()
                .forEach(session -> chatService.sendMessage(session, chatMessage));
    }
}
