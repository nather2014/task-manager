package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
		var requestDto = new com.example.taskmanager.dto.TaskRequestDto("Test Task", false);
		var responseDto = new com.example.taskmanager.dto.TaskResponseDto(1L, "Test Task", false);

		// When
		org.mockito.Mockito.when(taskService.create(requestDto)).thenReturn(responseDto);

		// Then
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/tasks")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isCreated())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.title").value("Test Task"))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.completed").value(false));
	}

	@Test
	void shouldGetAllTasks() throws Exception {
		// Given
		var responseDto1 = new com.example.taskmanager.dto.TaskResponseDto(1L, "Task 1", false);
		var responseDto2 = new com.example.taskmanager.dto.TaskResponseDto(2L, "Task 2", true);
		var responseList = java.util.List.of(responseDto1, responseDto2);

		// When
		org.mockito.Mockito.when(taskService.getAll()).thenReturn(responseList);

		// Then
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/tasks")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.length()").value(2))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].title").value("Task 1"))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].completed").value(false))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].title").value("Task 2"))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].completed").value(true));
	}


	@Test
	void shouldGetTaskById() throws Exception {
		// Given
		var responseDto = new com.example.taskmanager.dto.TaskResponseDto(1L, "Test Task", false);

		// When
		org.mockito.Mockito.when(taskService.getById(1L)).thenReturn(responseDto);

		// Then
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/api/v1/tasks/1")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.title").value("Test Task"))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.completed").value(false));
	}

	@Test
	void shouldDeleteTaskById() throws Exception {
		// Given
		org.mockito.Mockito.doNothing().when(taskService).deleteById(1L);

		// Then
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/v1/tasks/1")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON))
				.andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
	}


	@Test
	void contextLoads() {
	}

}
