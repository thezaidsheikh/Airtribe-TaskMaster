package com.airtribe.task_master.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskAssignRequestDto {
    @NotNull(message = "Assigned to is required")
    private Long assignedTo;
}
