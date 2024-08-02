package com.example.todolist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.Domain.model.Todo
import com.example.todolist.Domain.useCase.todo.AddTodoUseCase
import com.example.todolist.Domain.useCase.todo.DeleteTodoUseCase
import com.example.todolist.Domain.useCase.todo.GetTodoUseCase
import com.example.todolist.Domain.useCase.todo.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodoUseCase: GetTodoUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    private val _todo = MutableStateFlow<List<Todo>>(emptyList())
    val todo = _todo.asStateFlow()

    init {
        getTodo()
    }

    private fun getTodo() {
        viewModelScope.launch {
            getTodoUseCase().collect {
                _todo.value = it
            }
        }
    }

    fun addTodo(name: String) {
        viewModelScope.launch {
            addTodoUseCase(name)
            getTodo()
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            updateTodoUseCase(todo.id, todo.name)
            getTodo()
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            deleteTodoUseCase(todo)
            getTodo()
        }
    }

    fun getNameById(id: Long): String {
        return _todo.value.find { it.id == id }?.name ?: ""
    }
}