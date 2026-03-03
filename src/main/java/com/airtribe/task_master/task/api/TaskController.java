package com.airtribe.task_master.task.api;

import com.airtribe.task_master.common.provider.CurrentUserProvider;
import com.airtribe.task_master.common.response.ApiSuccess;
import com.airtribe.task_master.task.application.TaskService;
import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskStatus;
import com.airtribe.task_master.task.dto.AddCommentRequestDto;
import com.airtribe.task_master.task.dto.TaskAssignRequestDto;
import com.airtribe.task_master.task.dto.TaskRequestDto;
import com.airtribe.task_master.task.dto.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public TaskController(TaskService taskService, CurrentUserProvider currentUserProvider) {
        this.taskService = taskService;
        this.currentUserProvider = currentUserProvider;
    }

    // Create a new task
    @PostMapping
    @ApiSuccess(status = HttpStatus.CREATED, message = "Task created successfully")
    public Task createTask(@Valid @RequestBody TaskRequestDto request) {
        Long userId = currentUserProvider.getCurrentUserId();
        return taskService.createTask(request, userId);
    }

    // Assign team members to a task
    @PatchMapping("/{taskId}/assign")
    @ApiSuccess(status = HttpStatus.OK, message = "Task assigned successfully")
    public Task assignTask(@PathVariable Long taskId, @Valid @RequestBody TaskAssignRequestDto request) {
        Long userId = currentUserProvider.getCurrentUserId();
        return taskService.assignTask(request, taskId, userId);
    }

    // Update Status of a task
    @PatchMapping("/{taskId}/status/{status}")
    @ApiSuccess(status = HttpStatus.OK, message = "Task updated successfully")
    public void updateStatus(@PathVariable Long taskId, @PathVariable TaskStatus status) {
        Long userId = currentUserProvider.getCurrentUserId();
        taskService.updateStatus(taskId,status);
    }

    // Get all tasks of a user that are assigned to them
    @GetMapping("/assigned-to-me")
    public List<TaskResponse> getAllTasks() {
        Long userId = currentUserProvider.getCurrentUserId();
        return taskService.getAllTasksAssignedToUser(userId);
    }

    // Get all tasks created-by-me
    @GetMapping("/created-by-me")
    public List<Task> getAllTasksCreatedByMe() {
        Long userId = currentUserProvider.getCurrentUserId();
        return taskService.getAllTasksCreatedByUser(userId);
    }
}
