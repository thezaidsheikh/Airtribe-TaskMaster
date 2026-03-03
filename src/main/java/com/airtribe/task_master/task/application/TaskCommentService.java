package com.airtribe.task_master.task.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskComment;
import com.airtribe.task_master.task.dto.AddCommentRequestDto;
import com.airtribe.task_master.task.dto.TaskCommentResponseDto;
import com.airtribe.task_master.task.infrastructure.TaskCommentRepository;
import com.airtribe.task_master.task.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskCommentService(TaskCommentRepository taskCommentRepository, TaskRepository taskRepository) {
        this.taskCommentRepository = taskCommentRepository;
        this.taskRepository = taskRepository;
    }

    public TaskCommentResponseDto createComment(AddCommentRequestDto request, Long userId, Long taskId) throws NotFoundException {
        Task task = taskRepository.<Task>findByTaskId(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        TaskComment taskComment = TaskComment.builder().taskId(taskId).userId(userId).content(request.getContent()).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        taskCommentRepository.save(taskComment);
        return TaskCommentResponseDto.builder().commentId(taskComment.getCommentId()).userId(taskComment.getUserId()).content(taskComment.getContent()).createdAt(taskComment.getCreatedAt()).build();
    }

    public List<TaskCommentResponseDto> getAllComments(Long taskId) {
        return taskCommentRepository.findByTaskId(taskId);
    }
}
