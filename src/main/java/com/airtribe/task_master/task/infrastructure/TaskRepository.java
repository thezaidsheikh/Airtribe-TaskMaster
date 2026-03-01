package com.airtribe.task_master.task.infrastructure;

import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskId(Long taskId);
    Optional<Task> findByTitle(String title);
    List<Task> findAllByAssignedTo(Long assignedTo);
    Optional<Task> findByStatus(TaskStatus status);
}
