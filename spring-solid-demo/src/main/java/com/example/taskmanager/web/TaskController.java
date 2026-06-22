package com.example.taskmanager.web;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.web.dto.CreateTaskRequest;
import com.example.taskmanager.web.dto.TaskResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// SRP: this class only translates HTTP <-> domain calls.
// DIP: depends on the TaskService interface, never on TaskServiceImpl.
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request.getTitle(), request.getPriority());
        return TaskResponse.from(task);
    }

    @GetMapping
    public List<TaskResponse> listTasks() {
        return taskService.listTasks().stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return TaskResponse.from(taskService.getTask(id));
    }

    @PostMapping("/{id}/complete")
    public TaskResponse completeTask(@PathVariable Long id) {
        return TaskResponse.from(taskService.completeTask(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
