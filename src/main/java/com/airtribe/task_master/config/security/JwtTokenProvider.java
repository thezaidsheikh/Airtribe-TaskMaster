package com.airtribe.task_master.config.security;

import com.airtribe.task_master.user.application.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final JwtUtil jwtUtil;

    private final long ACCESS_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    public JwtTokenProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generateAccessToken(String email, Object user) {
        return jwtUtil.generateToken(email, user, ACCESS_EXPIRATION);
    }

    @Override
    public String generateRefreshToken(String email) {
        return jwtUtil.generateToken(email, null, REFRESH_EXPIRATION);
    }

    @Override
    public long getAccessTokenExpiry() {
        return ACCESS_EXPIRATION;
    }
}
