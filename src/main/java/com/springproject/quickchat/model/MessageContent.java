package com.springproject.quickchat.model;

public class MessageContent {
    private String value;

    private MessageContent(String value) {
        validate(value);
        this.value = value;
    }

    public static MessageContent from(String value) {
        return new MessageContent(value);
    }

    public void update(String newValue) {
        validate(newValue);
        this.value = newValue;
    }

    public String getValue() {
        return value;
    }

    public void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du message ne peut pas être vide.");
        }
        if (value.length() > 500) {
            throw new IllegalArgumentException("Le contenu du message ne doit pas dépasser 500 caractères.");
        }
    }
}
