package com.example.todolist.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ColorManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private var completedColorPreference by context.sharedPreference(COMPLETED_COLOR_KEY)
    private var notCompletedColorPreference by context.sharedPreference(NOT_COMPLETED_COLOR_KEY)
    fun getCompletedColor(): Int {
        return completedColorPreference
    }
    fun saveCompletedColor(color: Int) {
        completedColorPreference = color
    }
    fun getNotCompletedColor(): Int {
        return notCompletedColorPreference
    }
    fun saveNotCompletedColor(color: Int) {
        notCompletedColorPreference = color
    }
}