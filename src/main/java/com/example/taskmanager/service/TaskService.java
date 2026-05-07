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

  private final TaskRepository repo;

  public TaskResponseDto createTask(TaskRequestDto requestDto) {
    Task task = toEntity(requestDto);
    Task saved = repo.save(task);
    return toResponse(saved);
  }

  public void getAllTasks() {
    repo.findAll();
  }

  public TaskResponseDto getTaskById(Long id) {
    Task task = repo.findById(id).get();
    return toResponse(task);
  }

  private Task toEntity(TaskRequestDto requestDto) {
    Task task = new Task();
    task.setTitle(requestDto.title());
    task.setDescription(requestDto.description());
    task.setCompleted(false);
    return task;
  }

  private TaskResponseDto toResponse(Task task) {
    return new TaskResponseDto(task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.isCompleted());
  }

  public void deleteTask(Long id) {
    repo.deleteById(id);
  }

}
