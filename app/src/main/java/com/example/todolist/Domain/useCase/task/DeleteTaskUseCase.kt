package com.example.todolist.Domain.useCase.task

import com.example.todolist.Domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase@Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(taskId: Long) {
        return taskRepository.deleteTask(taskId)
    }
}