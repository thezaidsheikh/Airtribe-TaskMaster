package com.airtribe.task_master.task.dto;

import com.airtribe.task_master.task.domain.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskCommentResponseDto {
    private Long commentId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
}
