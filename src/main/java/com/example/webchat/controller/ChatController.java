package com.example.webchat.controller;

import com.example.webchat.dto.ChatRoom;
import com.example.webchat.dto.ChatRoomForm;
import com.example.webchat.services.ChatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public String createChatRoom(ChatRoomForm chatRoomForm){
        chatService.createChatRoom(chatRoomForm.getName());
        return "rooms";
    }

    @GetMapping("/")
    public String getAllChatRoom(Model model){
        model.addAttribute("rooms", chatService.findAllChatRoom());
        return "sd";
    }
}
