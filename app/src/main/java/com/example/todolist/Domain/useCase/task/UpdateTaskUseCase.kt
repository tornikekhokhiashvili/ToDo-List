package com.example.todolist.Domain.useCase.task

import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
    }

}