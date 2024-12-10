package com.springproject.quickchat.controller;

import com.springproject.quickchat.dto.FileDTO;
import com.springproject.quickchat.dto.MessageDTO;
import com.springproject.quickchat.model.Message;
import com.springproject.quickchat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(
            @RequestParam Long senderId,
            @RequestBody MessageDTO message,
            @RequestParam(required = false) FileDTO file
    ) {
        messageService.sendMessage(senderId, message, file);
    }


    @PutMapping("/edit")
    public Message editMessage(
            @RequestParam Long messageId,
            @RequestParam String newContent
    ) {
        return messageService.editMessage(messageId, newContent);
    }

}