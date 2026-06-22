package com.example.taskmanager.service;

import com.example.taskmanager.domain.Task;

// OCP: add a new listener bean to react to completions (e.g. notifications,
// auditing) without ever touching TaskServiceImpl.
public interface TaskCompletionListener {

    void onTaskCompleted(Task task);
}
