package com.springproject.quickchat.dto;

public class MessageRequest {
    private MessageDTO message;
    private FileDTO file;

    public MessageDTO getMessage() {
        return message;
    }

    public FileDTO getFile() {
        return file;
    }
}
