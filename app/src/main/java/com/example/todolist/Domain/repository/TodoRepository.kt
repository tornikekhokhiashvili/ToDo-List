package com.example.todolist.Domain.repository

import com.example.todolist.Domain.model.Todo
import kotlinx.coroutines.flow.Flow
interface TodoRepository {
    fun getTodos():Flow<List<Todo>>
    fun addTodo(name: String)
    fun updateTodoById(todoId: Long,name: String)
    fun deleteTodoById(todoId: Todo)
}