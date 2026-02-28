package com.airtribe.task_master.user.application;

import org.springframework.stereotype.Component;

@Component
public interface TokenProvider {
    String generateAccessToken(String email, Object user);
    String generateRefreshToken(String email);
    long getAccessTokenExpiry();
}
