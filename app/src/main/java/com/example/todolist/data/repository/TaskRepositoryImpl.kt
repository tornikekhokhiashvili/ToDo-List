package com.example.todolist.data.repository

import com.example.todolist.Domain.dataBase.DataBaseManagement
import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl@Inject constructor(
    private val dataBaseManager: DataBaseManagement
):TaskRepository{
    override fun getTasks(todoId: Long): Flow<List<Task>> {
        return dataBaseManager.getTasks(todoId)
    }

    override fun addTask(task: Task): Long {
        return dataBaseManager.addTask(task)
    }

    override fun updateTask(task: Task) {
        dataBaseManager.updateTask(task)
    }
    override fun deleteTask(taskId: Long) {
        dataBaseManager.deleteTask(taskId)
    }
}