package com.springproject.quickchat.controller;

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
    public void sendMessage(@RequestBody Message message) {
         messageService.sendMessage(message);
    }

    @PutMapping("/edit")
    public Message editMessage(
            @RequestParam String messageId,
            @RequestParam String newContent
    ) {
        return messageService.editMessage(messageId, newContent);
    }

}