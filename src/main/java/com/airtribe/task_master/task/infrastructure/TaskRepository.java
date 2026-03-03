package com.airtribe.task_master.task.infrastructure;

import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    <T> Optional<T> findByTaskId(Long taskId);
    <T> List<T> findByAssignedTo(Long assignedTo);
    <T> List<T> findByCreatedBy(Long createdBy);
}
