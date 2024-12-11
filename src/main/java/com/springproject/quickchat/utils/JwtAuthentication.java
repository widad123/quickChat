package com.springproject.quickchat.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {
    public JwtAuthentication(Object principal, Object credentials, Object details) {
        super(principal, credentials, null);
        setDetails(details);
    }
}
