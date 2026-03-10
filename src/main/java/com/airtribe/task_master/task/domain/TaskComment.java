package com.airtribe.task_master.task.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "taskComments", schema = "task_schema")
@Builder
@Data
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    private Long taskId;

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 2000)
    private String content;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateContent(String newContent) {
        this.content = newContent;
        this.updatedAt = LocalDateTime.now();
    }
}
