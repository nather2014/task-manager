import { useEffect, useState } from "react"
import api from "./api"

function App() {
  const [tasks, setTasks] = useState([])

  useEffect(() => {
    api.get("/tasks")
      .then((res) => setTasks(res.data))
  }, [])

  return (
    <div>
      <h1>Tasks</h1>

      <ul>
        {tasks.map((task: any) => (
          <li key={task.id}>
            {task.title}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default App