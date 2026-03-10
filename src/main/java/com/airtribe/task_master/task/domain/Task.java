package com.airtribe.task_master.task.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tasks", schema = "task_schema")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @NotNull
    private Long createdBy;

    @Column(nullable = true)
    private Long assignedTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
