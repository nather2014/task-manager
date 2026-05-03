package com.example.taskmanager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TaskController {

    private TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public void getAllTasks() {
        service.getAllTasks();
    }

    @PostMapping()
    public createTask(Task taskDto) {
        return service.createTask(taskDto);
    }
    
}