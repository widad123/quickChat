package com.springproject.quickchat.utils;

import java.util.UUID;

public class UuidToLongGenerator implements IdGenerator {

    @Override
    public Long generateId() {
        UUID uuid = UUID.randomUUID();
        return Math.abs(uuid.getMostSignificantBits());
    }
}

