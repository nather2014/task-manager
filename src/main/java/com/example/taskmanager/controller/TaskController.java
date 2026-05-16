package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
class TaskController {

  private final TaskService service;

  @PostMapping
  public ResponseEntity<TaskResponseDto> createTask(
      @RequestBody TaskRequestDto requestDto) {
    TaskResponseDto responseDto = service.createTask(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @GetMapping
  public void getAllTasks() {
    service.getAllTasks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getTaskById(id));
  }

  @DeleteMapping("/{id}")
  public void deleteTaskById(@PathVariable Long id) {
    service.deleteTaskById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponseDto> updateTaskById(@PathVariable Long id) {
    TaskResponseDto responseDto = service.updateTaskById(id);
    return ResponseEntity.ok(responseDto);
  }
}
