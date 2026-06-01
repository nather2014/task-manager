package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(@NotBlank String title, boolean completed) {
}
