package com.example.todolist.Domain.dataBase

import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.model.Todo
import kotlinx.coroutines.flow.Flow
interface DataBaseManagement {
    fun getTodos(): Flow<List<Todo>>
    fun addTodo(name: String):Long
    fun updateTodo(todoId: Long?, newName: String):Int
    fun deleteTodo(todo: Todo):Int
    fun getTasks(todoId: Long): Flow<List<Task>>
    fun addTask(task: Task):Long
    fun updateTask(task: Task):Int
    fun deleteTask(taskId: Long):Int
}