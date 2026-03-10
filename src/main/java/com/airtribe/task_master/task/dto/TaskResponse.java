package com.airtribe.task_master.task.dto;

import com.airtribe.task_master.task.domain.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
public record TaskResponse(Long taskId, String title, String description, LocalDate dueDate, TaskStatus status, Long createdBy, String createdByFirstName, String createdByLastName){
}
