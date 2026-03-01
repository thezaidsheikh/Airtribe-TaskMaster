package com.airtribe.task_master.task.dto;

import com.airtribe.task_master.task.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusDto {
    @NotNull(message = "status is required")
    private TaskStatus status;
}
