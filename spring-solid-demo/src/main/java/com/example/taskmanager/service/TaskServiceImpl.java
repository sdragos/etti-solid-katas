package com.example.taskmanager.service;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// SRP: this class only owns business rules (validation, completion rules,
// notifying listeners). It doesn't know about HTTP and doesn't know how
// tasks are actually stored.
// DIP: constructor injection wires in whatever TaskRepository and
// TaskCompletionListener beans exist - Spring is doing, automatically, the
// same thing the DIP kata does by hand.
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final List<TaskCompletionListener> completionListeners;

    public TaskServiceImpl(TaskRepository taskRepository, List<TaskCompletionListener> completionListeners) {
        this.taskRepository = taskRepository;
        this.completionListeners = completionListeners;
    }

    @Override
    public Task createTask(String title, Priority priority) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title must not be blank");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Task priority must not be null");
        }
        return taskRepository.save(new Task(null, title, priority, false));
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task completeTask(Long id) {
        Task task = getTask(id);
        if (task.isCompleted()) {
            throw new TaskAlreadyCompletedException(id);
        }
        task.markCompleted();
        Task saved = taskRepository.save(task);
        completionListeners.forEach(listener -> listener.onTaskCompleted(saved));
        return saved;
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.deleteById(id)) {
            throw new TaskNotFoundException(id);
        }
    }
}
