package com.airtribe.task_master.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class AuthResponseDto {
    private UserDetailDto user;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expiresAt;
}
