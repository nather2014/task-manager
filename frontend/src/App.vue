<template>
  <div class="container">
    <h1>Task Manager</h1>

    <div class="box">
      <h3>Add Task</h3>

      <input v-model="newTask.title" placeholder="Title" />

      <button @click="createTask">Add Task</button>
    </div>

    <div class="box">
      <h3>All Tasks</h3>

      <button @click="fetchTasks">Refresh</button>

      <ul>
        <li v-for="task in tasks" :key="task.id">
          {{ task.title }}

          <button @click="getTaskById(task.id)">Get</button>
          <button @click="startEdit(task)">Update</button>
          <button @click="deleteTask(task.id)">Delete</button>
        </li>
      </ul>
    </div>

    <div v-if="selectedTask" class="box">
      <h3>Task Details</h3>

      <p>
        <strong>{{ selectedTask.title }}</strong>
      </p>
    </div>

    <div v-if="editId" class="box">
      <h3>Update Task</h3>

      <input v-model="editTask.title" />

      <button @click="updateTask(editId)">Save Update</button>
      <button @click="cancelEdit">Cancel</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api/v1",
});

export default {
  data() {
    return {
      tasks: [],
      selectedTask: null,

      newTask: {
        title: "",
      },

      editTask: {
        title: "",
      },

      editId: null,
    };
  },

  mounted() {
    this.fetchTasks();
  },

  methods: {
    async createTask() {
      await api.post("/tasks", this.newTask);

      this.newTask = {
        title: "",
      };

      this.fetchTasks();
    },

    async fetchTasks() {
      const res = await api.get("/tasks");
      this.tasks = res.data;
    },

    async getTaskById(id) {
      const res = await api.get(`/tasks/${id}`);
      this.selectedTask = res.data;
    },

    startEdit(task) {
      this.editId = task.id;
      this.editTask = { ...task };
    },

    async updateTask(id) {
      await api.put(`/tasks/${id}`, this.editTask);

      this.cancelEdit();
      this.fetchTasks();
    },

    cancelEdit() {
      this.editId = null;

      this.editTask = {
        title: "",
      };
    },

    async deleteTask(id) {
      await api.delete(`/tasks/${id}`);
      this.fetchTasks();
    },
  },
};
</script>
