package com.example.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.service.TaskService;


@RestController
@RequestMapping("/api/v1/tasks")
class TaskController {

    private final TaskService service;

    TaskController(TaskService service) {
        this.service = service;
    }

    // create task
    @PostMapping()
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto requestDto) {
        TaskResponseDto responseDto = service.createTask(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //get all tasks
    @GetMapping
    public void getAllTasks() {
        service.getAllTasks();
    }

    // get task by id
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTaskById(id));
    }

}
