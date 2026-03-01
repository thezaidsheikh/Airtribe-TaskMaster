package com.airtribe.task_master.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "4234h3423432h43242384234h42343298";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, Object user, long expiration) {
        return Jwts.builder()
                   .setSubject(email)
                   .claim("user",user)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + expiration))
                   .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public Object fetchTokenClaim(String token) {
        Claims claims = getClaims(token);
        return claims.get("user",Object.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}