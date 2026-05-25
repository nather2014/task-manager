package com.example.taskmanager.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.mapper.TaskMapper;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;
  private final TaskMapper mapper;

  public TaskResponseDto create(TaskRequestDto requestDto) {
    Task task = mapper.toEntity(requestDto);
    Task saved = repository.save(task);
    return mapper.toResponse(saved);
  }

  public List<TaskResponseDto> getAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  public TaskResponseDto getById(Long id) {
    Task task = repository.findById(id).get();
    return mapper.toResponse(task);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public TaskResponseDto updateById(Long id, TaskRequestDto requestDto) {
    Task task = repository.findById(id).get();
    task.setTitle(requestDto.title());
    task.setCompleted(!task.isCompleted());
    Task updated = repository.save(task);
    return mapper.toResponse(updated);
  }

}
