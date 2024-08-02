package com.example.todolist.data.repository

import com.example.todolist.Domain.dataBase.DataBaseManagement
import com.example.todolist.Domain.model.Todo
import com.example.todolist.Domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dataBaseManager: DataBaseManagement
): TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        return dataBaseManager.getTodos()
    }
    override fun addTodo(name: String) {
        dataBaseManager.addTodo(name)
    }
    override fun updateTodoById(todoId: Long, name: String){
        dataBaseManager.updateTodo(todoId, name)
    }
    override fun deleteTodoById(todoId: Todo) {
        dataBaseManager.deleteTodo(todoId)
    }
}