package com.example.taskmanager;

import org.springframework.stereotype.Service;

@Service
class TaskService {

    private TaskRepository repo;
    
    TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public void getAllTasks() {
        repo.findAll();
    }

    public Object createTask(TaskDto dto) {

    }
}