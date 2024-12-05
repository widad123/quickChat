package com.springproject.quickchat.model;

public record MessageDTO(
    String sender,
    String recipient,
    String content,
    String timestamp
){}

