package com.example.todolist.Domain.useCase.todo

import com.example.todolist.Domain.model.Todo
import com.example.todolist.Domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(todoId: Todo) {
        todoRepository.deleteTodoById(todoId)
    }
}