package com.example.taskmanager.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public TaskResponseDto createTask(TaskRequestDto requestDto) {
    Task task = toEntity(requestDto);
    Task saved = repository.save(task);
    return toResponse(saved);
  }

  public void getAllTasks() {
    repository.findAll();
  }

  public TaskResponseDto getTaskById(Long id) {
    Task task = repository.findById(id).get();
    return toResponse(task);
  }

  private Task toEntity(TaskRequestDto requestDto) {
    Task task = new Task();
    // task.setTitle(requestDto.title());
    // task.setDescription(requestDto.description());
    // task.setCompleted(false);
    return task;
  }

  private TaskResponseDto toResponse(Task task) {
    return new TaskResponseDto(1L, "title", false);
  }

  public void deleteTaskById(Long id) {
    repository.deleteById(id);
  }

  public TaskResponseDto updateTaskById(Long id) {
    return new TaskResponseDto();
  }

}
