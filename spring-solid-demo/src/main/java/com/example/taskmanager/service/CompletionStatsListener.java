package com.example.taskmanager.service;

import com.example.taskmanager.domain.Task;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CompletionStatsListener implements TaskCompletionListener {

    private final AtomicInteger completedCount = new AtomicInteger();

    @Override
    public void onTaskCompleted(Task task) {
        completedCount.incrementAndGet();
    }

    public int getCompletedCount() {
        return completedCount.get();
    }
}
