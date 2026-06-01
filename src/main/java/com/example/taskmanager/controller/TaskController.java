package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

  TaskController(TaskService service) {
    this.service = service;
  }

  private final TaskService service;

  @PostMapping
  public ResponseEntity<TaskResponseDto> createTask(
      @Valid @RequestBody TaskRequestDto requestDto) {
    TaskResponseDto responseDto = service.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @GetMapping
  public ResponseEntity<List<TaskResponseDto>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskResponseDto> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    service.deleteById(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponseDto> updateById(@PathVariable @Min(1) Long id,
      @RequestBody TaskRequestDto requestDto) {
    TaskResponseDto responseDto = service.updateById(id, requestDto);
    return ResponseEntity.ok(responseDto);
  }
}
