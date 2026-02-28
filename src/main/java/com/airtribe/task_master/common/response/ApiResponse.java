package com.airtribe.task_master.common.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private T data;

    public ApiResponse(int status, String message, String path, T data) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }
}