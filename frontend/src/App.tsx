// src/App.tsx
import React, { useEffect, useState, ChangeEvent, FormEvent } from 'react';
import { taskApi } from './api';
import { Task, Priority, TaskStatus } from './types';
import { CheckSquare, LayoutDashboard, Plus, Clock, CheckCircle2, AlertCircle, Trash2 } from 'lucide-react';

declare global {
    namespace JSX {
        interface IntrinsicElements {
            [elemName: string]: any;
        }
    }
}

export default function App() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [title, setTitle] = useState('');
    const [desc, setDesc] = useState('');
    const [priority, setPriority] = useState<Priority>('MEDIUM');

    // Load initial task array from Spring backend on load
    useEffect(() => {
        loadTasks();
    }, []);

    const loadTasks = async () => {
        try {
            const data = await taskApi.getAll();
            setTasks(data);
        } catch (err) {
            console.error("Could not sync with backend database: ", err);
        }
    };

    const handleCreateTask = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!title.trim()) return;

        try {
            await taskApi.create({
                title,
                description: desc,
                priority,
                status: 'TODO'
            });
            setTitle('');
            setDesc('');
            setPriority('MEDIUM');
            loadTasks(); // Automatically update task list state
        } catch (err) {
            alert("Error creating task entry");
        }
    };

    const handleCycleStatus = async (task: Task) => {
        // Simple forward state cycler logic: TODO -> IN_PROGRESS -> DONE
        const nextStatus: Record<TaskStatus, TaskStatus> = {
            'TODO': 'IN_PROGRESS',
            'IN_PROGRESS': 'DONE',
            'DONE': 'TODO'
        };
        
        try {
            await taskApi.updateStatus(task.id, nextStatus[task.status]);
            loadTasks();
        } catch (err) {
            console.error("Error updating field status: ", err);
        }
    };

    const handleDelete = async (id: number) => {
        try {
            await taskApi.delete(id);
            loadTasks();
        } catch (err) {
            console.error("Error wiping data entry: ", err);
        }
    };

    return (
        <div className="flex h-screen overflow-hidden bg-gray-50 text-gray-900">
            {/* CLEAN BRANDING SIDEBAR */}
            <aside className="w-64 bg-white border-r border-gray-200 hidden md:flex flex-col p-6">
                <div className="flex items-center gap-3 mb-8">
                    <div className="bg-indigo-600 text-white p-2 rounded-lg">
                        <CheckSquare className="w-6 h-6" />
                    </div>
                    <span className="text-xl font-bold text-gray-800">TaskEngine</span>
                </div>
                <nav className="space-y-1 flex-1">
                    <a href="#" className="flex items-center gap-3 px-4 py-2.5 rounded-lg bg-indigo-50 text-indigo-700 font-medium">
                        <LayoutDashboard className="w-5 h-5" /> Workspace Board
                    </a>
                </nav>
            </aside>

            {/* MAIN APP CONTAINER */}
            <main className="flex-1 flex flex-col overflow-y-auto">
                <header className="bg-white border-b border-gray-200 px-8 py-5 flex items-center justify-between sticky top-0 z-10">
                    <h1 className="text-2xl font-bold text-gray-800">Operational Dashboard</h1>
                </header>

                <div className="p-8 max-w-6xl w-full mx-auto space-y-8">
                    {/* TASK CREATION FORM COMPONENT */}
                    <section className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
                        <h2 className="text-sm font-semibold text-gray-500 uppercase tracking-wider mb-4">Initialize Workspace Task</h2>
                        <form onSubmit={handleCreateTask} className="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
                            <div className="md:col-span-2 space-y-1">
                                <input 
                                    type="text" 
                                    placeholder="Task title or user story..." 
                                    value={title} 
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => setTitle(e.target.value)}
                                    className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm focus:outline-none focus:border-indigo-500"
                                    required 
                                />
                                <input 
                                    type="text" 
                                    placeholder="Add brief description contexts..." 
                                    value={desc} 
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => setDesc(e.target.value)}
                                    className="w-full border border-gray-200 rounded-lg px-3 py-2 text-xs text-gray-500 focus:outline-none focus:border-indigo-500"
                                />
                            </div>
                            <div>
                                <select 
                                    value={priority} 
                                    onChange={(e: ChangeEvent<HTMLSelectElement>) => setPriority(e.target.value as Priority)}
                                    className="w-full border border-gray-200 rounded-lg px-3 py-2 text-sm bg-white focus:outline-none focus:border-indigo-500"
                                >
                                    <option value="LOW">Low Priority</option>
                                    <option value="MEDIUM">Medium Priority</option>
                                    <option value="HIGH">High Priority</option>
                                </select>
                            </div>
                            <button type="submit" className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 rounded-lg font-medium text-sm flex items-center justify-center gap-2 transition shadow-sm">
                                <Plus className="w-4 h-4" /> Commit Task
                            </button>
                        </form>
                    </section>

                    {/* LIVE BOARD SYSTEM LANES */}
                    <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
                        {(['TODO', 'IN_PROGRESS', 'DONE'] as TaskStatus[]).map(status => {
                            const filtered = tasks.filter(t => t.status === status);
                            return (
                                <div key={status} className="bg-gray-100/60 p-4 rounded-xl border border-gray-200/80 min-h-[400px] flex flex-col">
                                    <div className="flex items-center justify-between mb-4 px-1">
                                        <h3 className="font-bold text-gray-700 tracking-wide text-sm uppercase">
                                            {status.replace('_', ' ')}
                                        </h3>
                                        <span className="bg-gray-200 text-gray-600 text-xs px-2.5 py-0.5 rounded-full font-bold">
                                            {filtered.length}
                                        </span>
                                    </div>

                                    <div className="space-y-3 flex-1 overflow-y-auto">
                                        {filtered.map(task => (
                                            <div key={task.id} className="bg-white p-4 rounded-lg border border-gray-200 shadow-sm flex flex-col justify-between hover:border-indigo-300 transition-all group">
                                                <div>
                                                    <div className="flex items-center justify-between mb-2">
                                                        <span className={`text-[10px] font-bold px-2 py-0.5 rounded-full uppercase ${
                                                            task.priority === 'HIGH' ? 'bg-rose-50 text-rose-700' :
                                                            task.priority === 'MEDIUM' ? 'bg-amber-50 text-amber-700' : 'bg-slate-100 text-slate-700'
                                                        }`}>
                                                            {task.priority}
                                                        </span>
                                                        <button onClick={() => handleDelete(task.id)} className="text-gray-300 hover:text-rose-600 transition opacity-0 group-hover:opacity-100">
                                                            <Trash2 className="w-4 h-4" />
                                                        </button>
                                                    </div>
                                                    <h4 className="font-bold text-gray-800 text-sm mb-1">{task.title}</h4>
                                                    <p className="text-xs text-gray-500 mb-4">{task.description}</p>
                                                </div>

                                                <button 
                                                    onClick={() => handleCycleStatus(task)}
                                                    className="w-full mt-2 text-center border border-gray-100 bg-gray-50 hover:bg-indigo-50 hover:text-indigo-600 text-[11px] font-semibold py-1.5 rounded-md transition flex items-center justify-center gap-1 text-gray-600"
                                                >
                                                    {task.status === 'TODO' && <><Clock className="w-3 h-3" /> Start Progress</>}
                                                    {task.status === 'IN_PROGRESS' && <><CheckCircle2 className="w-3 h-3" /> Complete Task</>}
                                                    {task.status === 'DONE' && <><AlertCircle className="w-3 h-3" /> Reset to Backlog</>}
                                                </button>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            );
                        })}
                    </section>
                </div>
            </main>
        </div>
    );
}