package com.example.todolist.Domain.useCase.task

import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(todoId: Long): Flow<List<Task>> {
        return taskRepository.getTasks(todoId)
    }
}