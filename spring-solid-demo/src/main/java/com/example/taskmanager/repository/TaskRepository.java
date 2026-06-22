package com.example.taskmanager.repository;

import com.example.taskmanager.domain.Task;

import java.util.List;
import java.util.Optional;

// DIP + ISP: the service tier depends on this narrow abstraction, never on
// a concrete storage technology. Only the methods the service actually
// needs are here.
public interface TaskRepository {

    Task save(Task task);

    List<Task> findAll();

    Optional<Task> findById(Long id);

    boolean deleteById(Long id);
}
