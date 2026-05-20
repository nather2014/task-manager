// src/api.ts
import { Task, CreateTaskDTO } from './types';

const API_BASE = 'http://localhost:8080/api/tasks';

export const taskApi = {
    // GET /api/tasks
    getAll: async (): Promise<Task[]> => {
        const res = await fetch(API_BASE);
        if (!res.ok) throw new Error('Network error fetching tasks');
        return res.json();
    },

    // POST /api/tasks
    create: async (task: CreateTaskDTO): Promise<Task> => {
        const res = await fetch(API_BASE, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(task),
        });
        if (!res.ok) throw new Error('Failed to create task');
        return res.json();
    },

    // PATCH /api/tasks/{id} - Perfect for changing task columns smoothly
    updateStatus: async (id: number, status: Task['status']): Promise<Task> => {
        const res = await fetch(`${API_BASE}/${id}`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status }),
        });
        if (!res.ok) throw new Error('Failed to update task status');
        return res.json();
    },

    // DELETE /api/tasks/{id}
    delete: async (id: number): Promise<void> => {
        const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('Failed to delete task');
    }
};