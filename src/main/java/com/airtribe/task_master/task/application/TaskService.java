package com.airtribe.task_master.task.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.common.provider.CurrentUserProvider;
import com.airtribe.task_master.user.application.UserLookupService;
import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskStatus;
import com.airtribe.task_master.task.dto.TaskRequestDto;
import com.airtribe.task_master.task.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;
    private final UserLookupService userLookupService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserLookupService userLookupService) {
        this.taskRepository = taskRepository;
        this.userLookupService = userLookupService;
    }

    // Create task
    public Task createTask(TaskRequestDto task) throws NotFoundException {
        // Validate user exists
        userLookupService.getUserById(task.getAssignedTo());

        Task newTask = Task.builder().title(task.getTitle()).description(task.getDescription()).dueDate(task.getDueDate())
                           .assignedTo(task.getAssignedTo()).status(TaskStatus.PENDING).createdAt(LocalDateTime.now())
                           .updatedAt(LocalDateTime.now()).build();

        return taskRepository.save(newTask);
    }

    // Update task status to complete
    public void updateStatus(Long taskId, TaskStatus status) throws NotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status.toString()));
        taskRepository.save(task);
    }

    public List<Task> getAllTasksOfUser(Long userId) {

        List<Task> tasks = taskRepository.findAllByAssignedTo(userId);
        return tasks;
    }
}
