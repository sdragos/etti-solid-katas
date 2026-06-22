package com.example.taskmanager.service;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

// This whole test class is the payoff of DIP: TaskServiceImpl's business
// rules are verified with mocked collaborators - no Spring context, no
// real storage, no HTTP.
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCompletionListener loggingListener;

    @Mock
    private TaskCompletionListener statsListener;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(taskRepository, List.of(loggingListener, statsListener));
    }

    @Test
    void createTask_savesAndReturnsTask() {
        Task saved = new Task(1L, "Write slides", Priority.HIGH, false);
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        Task result = taskService.createTask("Write slides", Priority.HIGH);

        assertThat(result).isEqualTo(saved);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_rejectsBlankTitle() {
        assertThatThrownBy(() -> taskService.createTask("   ", Priority.LOW))
                .isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(taskRepository);
    }

    @Test
    void completeTask_marksCompleteAndNotifiesAllListeners() {
        Task existing = new Task(1L, "Write slides", Priority.HIGH, false);
        Task afterComplete = new Task(1L, "Write slides", Priority.HIGH, true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(afterComplete);

        Task result = taskService.completeTask(1L);

        assertThat(result.isCompleted()).isTrue();
        verify(loggingListener).onTaskCompleted(afterComplete);
        verify(statsListener).onTaskCompleted(afterComplete);
    }

    @Test
    void completeTask_rejectsAlreadyCompletedTask() {
        Task alreadyDone = new Task(1L, "Write slides", Priority.HIGH, true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(alreadyDone));

        assertThatThrownBy(() -> taskService.completeTask(1L))
                .isInstanceOf(TaskAlreadyCompletedException.class);
        verifyNoInteractions(loggingListener, statsListener);
    }

    @Test
    void getTask_throwsWhenMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(99L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
