package com.airtribe.task_master.task.application;

import com.airtribe.task_master.common.exception.NotFoundException;
import com.airtribe.task_master.task.dto.TaskAssignRequestDto;
import com.airtribe.task_master.task.dto.TaskResponse;
import com.airtribe.task_master.user.contract.UserDetail;
import com.airtribe.task_master.user.contract.UserLookupService;
import com.airtribe.task_master.task.domain.Task;
import com.airtribe.task_master.task.domain.TaskStatus;
import com.airtribe.task_master.task.dto.TaskRequestDto;
import com.airtribe.task_master.task.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserLookupService userLookupService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserLookupService userLookupService) {
        this.taskRepository = taskRepository;
        this.userLookupService = userLookupService;
    }

    // Create task
    public Task createTask(TaskRequestDto task, Long createdBy) throws NotFoundException {
        Task newTask = Task.builder().title(task.getTitle()).description(task.getDescription()).dueDate(task.getDueDate())
                           .createdBy(createdBy).status(TaskStatus.OPEN).createdAt(LocalDateTime.now())
                           .updatedAt(LocalDateTime.now()).build();

        return taskRepository.save(newTask);
    }

    // Assign task to a team member
    public Task assignTask(TaskAssignRequestDto taskAssignRequestDto, Long taskId, Long createdBy) throws NotFoundException {
        // Check is assignedTo user is a valid team member or not
        Task task = taskRepository.<Task>findByTaskId(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setAssignedTo(taskAssignRequestDto.getAssignedTo());
        return taskRepository.save(task);
    }

    // Update task status to complete
    public void updateStatus(Long taskId, TaskStatus status,Long userId) throws NotFoundException {
        Task task = taskRepository.<Task>findByTaskId(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        if(task.getAssignedTo() != userId) throw new RuntimeException("Unauthorized access");
        task.setStatus(TaskStatus.valueOf(status.toString()));
        taskRepository.save(task);
    }

    // Get all tasks assigned to a user
    public List<TaskResponse> getAllTasksAssignedToUser(Long userId) {
        List<Task> tasks = taskRepository.<Task>findByAssignedTo(userId);
        List<TaskResponse> taskResponses = new ArrayList<>();
        if(!tasks.isEmpty()) {
            List<Long> createdByUserIds = tasks.stream().map(Task::getCreatedBy).distinct().collect(Collectors.toList());
            Map<Long, UserDetail> createdByUserTasks = userLookupService.findAllByIds(createdByUserIds);

            tasks.forEach(task -> {
                TaskResponse taskResponse = TaskResponse.builder().taskId(task.getTaskId()).title(task.getTitle()).description(task.getDescription()).dueDate(task.getDueDate())
                                                        .status(task.getStatus()).createdBy(task.getCreatedBy()).createdByFirstName(createdByUserTasks.get(task.getCreatedBy()).firstName())
                                                        .createdByLastName(createdByUserTasks.get(task.getCreatedBy()).lastName()).build();
                taskResponses.add(taskResponse);
            });
        }
        return taskResponses;
    }

    // Get all tasks created by user
    public List<Task> getAllTasksCreatedByUser(Long userId) {
        List<Task> tasks = taskRepository.findByCreatedBy(userId);
        return tasks;
    }
}
