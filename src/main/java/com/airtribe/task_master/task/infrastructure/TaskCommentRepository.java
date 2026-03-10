package com.airtribe.task_master.task.infrastructure;

import com.airtribe.task_master.task.domain.TaskComment;
import com.airtribe.task_master.task.dto.TaskCommentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskCommentResponseDto> findByTaskId(Long taskId);
}
