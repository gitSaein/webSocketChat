package com.example.webchat.dto;

import com.example.webchat.contants.MessageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ChatMessage {
    String id;
    String chatRoomId;
    String sender;
    String message;
    MessageType messageType;
}
