package com.example.taskmanager.repository;

import com.example.taskmanager.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// No database here on purpose - this is the only class that knows storage
// is "just a map". Swap it for a JPA/JDBC implementation later and nothing
// in the service or web tier has to change (DIP).
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            Task withId = new Task(idGenerator.incrementAndGet(), task.getTitle(), task.getPriority(), task.isCompleted());
            store.put(withId.getId(), withId);
            return withId;
        }
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}
