package com.example.taskmanager.web.dto;

import com.example.taskmanager.domain.Priority;

public class CreateTaskRequest {

    private String title;
    private Priority priority;

    public CreateTaskRequest() {
    }

    public CreateTaskRequest(String title, Priority priority) {
        this.title = title;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
