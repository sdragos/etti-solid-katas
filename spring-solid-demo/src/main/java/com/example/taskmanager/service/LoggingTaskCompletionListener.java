package com.example.taskmanager.service;

import com.example.taskmanager.domain.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingTaskCompletionListener implements TaskCompletionListener {

    private static final Logger log = LoggerFactory.getLogger(LoggingTaskCompletionListener.class);

    @Override
    public void onTaskCompleted(Task task) {
        log.info("Task completed: [{}] {}", task.getId(), task.getTitle());
    }
}
