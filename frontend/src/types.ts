// src/types.ts

export type Priority = 'LOW' | 'MEDIUM' | 'HIGH';
export type TaskStatus = 'TODO' | 'IN_PROGRESS' | 'DONE';

// Matches the exact JSON structural contract emitted by Spring Boot
export interface Task {
    id: number;
    title: string;
    description: string;
    priority: Priority;
    status: TaskStatus;
    dueDate?: string; 
}

// Struct utilized when dispatching a payload to POST /api/tasks
export interface CreateTaskDTO {
    title: string;
    description: string;
    priority: Priority;
    status: TaskStatus;
    dueDate?: string;
}