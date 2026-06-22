package com.example.taskmanager.domain;

public class Task {

    private final Long id;
    private final String title;
    private final Priority priority;
    private boolean completed;

    public Task(Long id, String title, Priority priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = completed;
    }

    public void markCompleted() {
        this.completed = true;
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
