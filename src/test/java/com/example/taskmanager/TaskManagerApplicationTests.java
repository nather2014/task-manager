package com.example.taskmanager;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = com.example.taskmanager.controller.TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskManagerApplicationTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private TaskService taskService;

        @Test
        void shouldCreateTask() throws Exception {
                // Given
                var requestDto = new TaskRequestDto("Test Task", false);
                var responseDto = new TaskResponseDto(1L, "Test Task", false);

                // When
                Mockito.when(taskService.create(requestDto)).thenReturn(responseDto);

                // Then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDto)))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Task"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.completed").value(false));
        }

        @Test
        void shouldGetAllTasks() throws Exception {
                // Given
                var responseDto1 = new TaskResponseDto(1L, "Task 1", false);
                var responseDto2 = new TaskResponseDto(2L, "Task 2", true);
                var responseList = java.util.List.of(responseDto1, responseDto2);

                // When
                Mockito.when(taskService.getAll()).thenReturn(responseList);

                // Then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.length()")
                                                .value(2))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title")
                                                .value("Task 1"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[0].completed")
                                                .value(false))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title")
                                                .value("Task 2"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$[1].completed")
                                                .value(true));
        }

        @Test
        void shouldGetTaskById() throws Exception {
                // Given
                var responseDto = new TaskResponseDto(1L, "Test Task", false);

                // When
                Mockito.when(taskService.getById(1L)).thenReturn(responseDto);

                // Then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                                                                .value("Test Task"))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.completed")
                                                                .value(false));
        }

        @Test
        void shouldDeleteTaskById() throws Exception {
                // Given
                Mockito.doNothing().when(taskService).deleteById(1L);

                // Then
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void contextLoads() {
        }

}
