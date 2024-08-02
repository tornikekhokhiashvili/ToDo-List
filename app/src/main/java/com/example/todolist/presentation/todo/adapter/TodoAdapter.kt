package com.example.todolist.presentation.todo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.Domain.model.Todo

class TodoAdapter (
    private val onDeleteClick: ((Todo) -> Unit),
    private val onEditClick: ((Todo) -> Unit),
    private val onNavigateClick: ((Long) -> Unit),
):ListAdapter<Todo,TodoViewHolder>(TodoDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position),onDeleteClick,onEditClick,onNavigateClick)
    }

}