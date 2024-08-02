package com.example.todolist.Domain.useCase.todo

import com.example.todolist.Domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(todoId: Long, newName: String) {
        todoRepository.updateTodoById(todoId, newName)
    }

}