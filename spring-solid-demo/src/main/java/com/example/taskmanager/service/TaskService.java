package com.example.taskmanager.service;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;

import java.util.List;

// DIP: the web tier depends on this interface, never on TaskServiceImpl.
public interface TaskService {

    Task createTask(String title, Priority priority);

    List<Task> listTasks();

    Task getTask(Long id);

    Task completeTask(Long id);

    void deleteTask(Long id);
}
