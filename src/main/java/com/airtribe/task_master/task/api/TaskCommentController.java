package com.airtribe.task_master.task.api;

import com.airtribe.task_master.common.provider.CurrentUserProvider;
import com.airtribe.task_master.common.response.ApiSuccess;
import com.airtribe.task_master.task.application.TaskCommentService;
import com.airtribe.task_master.task.dto.AddCommentRequestDto;
import com.airtribe.task_master.task.dto.TaskCommentResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
public class TaskCommentController {
    private final TaskCommentService taskCommentService;
    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public TaskCommentController(TaskCommentService taskCommentService, CurrentUserProvider currentUserProvider) {
        this.taskCommentService = taskCommentService;
        this.currentUserProvider = currentUserProvider;
    }

    // Add comment on a task
    @PostMapping
    @ApiSuccess(status = HttpStatus.CREATED, message = "Comment added successfully")
    public TaskCommentResponseDto addComment(@PathVariable Long taskId, @Valid @RequestBody AddCommentRequestDto request) {
        Long userId = currentUserProvider.getCurrentUserId();
        return taskCommentService.createComment(request, userId, taskId);
    }

    // Get all comments of a task
    @GetMapping
    public List<TaskCommentResponseDto> getAllComments(@PathVariable Long taskId) {
        return taskCommentService.getAllComments(taskId);
    }
}
