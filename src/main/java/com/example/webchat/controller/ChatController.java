package com.example.webchat.controller;

import com.example.webchat.dto.ChatRoom;
import com.example.webchat.services.ChatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(path = "/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Test Sample", tags = "sample")
    public ChatRoom createChatRoom(@RequestParam String name){
        return chatService.createChatRoom(name);
    }

    @GetMapping("/")
    @ApiOperation(value = "Test Sample", tags = "sample")
    public String getAllChatRoom(Model model){
        model.addAttribute("rooms", chatService.findAllChatRoom());
        return "rooms";
    }
}
