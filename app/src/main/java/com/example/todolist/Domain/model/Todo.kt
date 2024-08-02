package com.example.todolist.Domain.model

import java.util.UUID

data class Todo(
    val id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val name: String
)
