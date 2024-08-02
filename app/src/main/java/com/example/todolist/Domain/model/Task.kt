package com.example.todolist.Domain.model

import java.util.UUID

data class Task (
    val id:Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val name: String,
    val isCompleted: Int =0,
    val todoId: Long
)