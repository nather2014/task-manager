package com.example.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.model.Task;

@Component
public class TaskMapper {

    public TaskResponseDto toResponse(Task task) {
        return new TaskResponseDto(task.getId(), task.getTitle(), task.isCompleted());
    }

    public Task toEntity(TaskRequestDto requestDto) {
        Task task = new Task();
        task.setTitle(requestDto.title());
        task.setCompleted(requestDto.completed());
        return task;
    }

}
