package com.example.taskmanager.web.dto;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;

public class TaskResponse {

    private final Long id;
    private final String title;
    private final Priority priority;
    private final boolean completed;

    public TaskResponse(Long id, String title, Priority priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = completed;
    }

    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getPriority(), task.isCompleted());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }
}
