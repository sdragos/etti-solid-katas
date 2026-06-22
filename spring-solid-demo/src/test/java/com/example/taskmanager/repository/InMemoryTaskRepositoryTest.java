package com.example.taskmanager.repository;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryTaskRepositoryTest {

    private final InMemoryTaskRepository repository = new InMemoryTaskRepository();

    @Test
    void save_assignsIdToNewTask() {
        Task saved = repository.save(new Task(null, "Buy milk", Priority.LOW, false));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).contains(saved);
    }

    @Test
    void save_updatesExistingTask() {
        Task saved = repository.save(new Task(null, "Buy milk", Priority.LOW, false));
        saved.markCompleted();

        repository.save(saved);

        assertThat(repository.findById(saved.getId()).orElseThrow().isCompleted()).isTrue();
    }

    @Test
    void findAll_returnsAllSavedTasks() {
        repository.save(new Task(null, "A", Priority.LOW, false));
        repository.save(new Task(null, "B", Priority.MEDIUM, false));

        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    void deleteById_removesTaskAndReturnsTrue() {
        Task saved = repository.save(new Task(null, "Buy milk", Priority.LOW, false));

        assertThat(repository.deleteById(saved.getId())).isTrue();
        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    @Test
    void deleteById_returnsFalseWhenMissing() {
        assertThat(repository.deleteById(123L)).isFalse();
    }
}
