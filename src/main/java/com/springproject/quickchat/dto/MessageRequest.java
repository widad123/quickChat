package com.springproject.quickchat.dto;

public class MessageRequest {
    private Long senderId;
    private MessageDTO message;
    private FileDTO file;

    public Long getSenderId() {
        return senderId;
    }

    public MessageDTO getMessage() {
        return message;
    }

    public FileDTO getFile() {
        return file;
    }
}
