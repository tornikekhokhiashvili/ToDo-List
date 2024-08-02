package com.example.todolist.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.utils.ColorManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val colorManager: ColorManager
) : ViewModel() {
    private val _completedColor = MutableStateFlow(0)
    val completedColor = _completedColor.asStateFlow()

    private val _notCompletedColor = MutableStateFlow(0)
    val notCompletedColor = _notCompletedColor.asStateFlow()

    init {
        getCompletedColor()
        getNotCompletedColor()
    }

    private fun getCompletedColor() {
        viewModelScope.launch {
            _completedColor.value = colorManager.getCompletedColor()
        }
    }

    fun setCompletedColor(color: Int) {
        viewModelScope.launch {
            colorManager.saveCompletedColor(color)
        }
    }

    fun getNotCompletedColor() {
        viewModelScope.launch {
            _notCompletedColor.value = colorManager.getNotCompletedColor()
        }
    }

    fun setNotCompletedColor(color: Int) {
        viewModelScope.launch {
            colorManager.saveNotCompletedColor(color)
        }
    }
}