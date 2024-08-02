package com.example.todolist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.Domain.model.Task
import com.example.todolist.Domain.useCase.task.AddTaskUseCase
import com.example.todolist.Domain.useCase.task.DeleteTaskUseCase
import com.example.todolist.Domain.useCase.task.GetTaskUseCase
import com.example.todolist.Domain.useCase.task.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
):ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    fun getTasks(id: Long) {
        viewModelScope.launch {
            getTaskUseCase(id).collect{
                _tasks.value = it
            }
        }
    }
    fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase(task)
            getTasks(task.todoId)
        }
    }
    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
            getTasks(task.todoId)
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task.id)
            getTasks(task.todoId)
        }
    }
}