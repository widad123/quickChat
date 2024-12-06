package com.springproject.quickchat.controller;

import com.springproject.quickchat.model.MessageDTO;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageDTO message) {
        messageService.sendMessage(message);
    }

}