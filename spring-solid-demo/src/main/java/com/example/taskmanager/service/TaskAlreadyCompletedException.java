package com.example.taskmanager.service;

public class TaskAlreadyCompletedException extends RuntimeException {

    public TaskAlreadyCompletedException(Long id) {
        super("Task already completed: " + id);
    }
}
