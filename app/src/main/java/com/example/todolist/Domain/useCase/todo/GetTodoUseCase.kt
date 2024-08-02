package com.example.todolist.Domain.useCase.todo

import com.example.todolist.Domain.model.Todo
import com.example.todolist.Domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<Todo>> {
        return todoRepository.getTodos()
    }
}