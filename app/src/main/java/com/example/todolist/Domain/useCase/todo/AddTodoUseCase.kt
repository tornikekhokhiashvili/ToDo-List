package com.example.todolist.Domain.useCase.todo

import com.example.todolist.Domain.repository.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(name: String) {
         todoRepository.addTodo(name)
    }
}