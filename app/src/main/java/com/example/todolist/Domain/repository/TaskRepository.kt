package com.example.todolist.Domain.repository

import com.example.todolist.Domain.model.Task
import kotlinx.coroutines.flow.Flow
interface TaskRepository {
    fun getTasks(todoId: Long): Flow<List<Task>>
    fun addTask(task: Task):Long
    fun updateTask(task: Task)
    fun deleteTask(taskId: Long)
}