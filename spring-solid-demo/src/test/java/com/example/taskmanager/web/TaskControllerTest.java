package com.example.taskmanager.web;

import com.example.taskmanager.domain.Priority;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.service.TaskAlreadyCompletedException;
import com.example.taskmanager.service.TaskNotFoundException;
import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// DIP again, one layer up: the controller is tested with a mocked
// TaskService - no real business logic, no real storage, just HTTP wiring.
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void getTask_returns200WithBody() throws Exception {
        when(taskService.getTask(1L)).thenReturn(new Task(1L, "Write slides", Priority.HIGH, false));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Write slides"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void getTask_returns404WhenMissing() throws Exception {
        when(taskService.getTask(99L)).thenThrow(new TaskNotFoundException(99L));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_returns201() throws Exception {
        when(taskService.createTask(eq("Write slides"), eq(Priority.HIGH)))
                .thenReturn(new Task(1L, "Write slides", Priority.HIGH, false));

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Write slides\",\"priority\":\"HIGH\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void completeTask_returns409WhenAlreadyCompleted() throws Exception {
        when(taskService.completeTask(1L)).thenThrow(new TaskAlreadyCompletedException(1L));

        mockMvc.perform(post("/api/tasks/1/complete"))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteTask_returns204() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
