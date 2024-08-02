package com.example.todolist.presentation.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Domain.model.Task
import com.example.todolist.databinding.ItemTaskBinding

class TaskViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        task: Task,
        onEditClick: ((Task) -> Unit),
        onDeleteClick: ((Task) -> Unit),
        onUpdate: ((Task) -> Unit)
    ) {
        binding.apply {
            checkBox.text = task.name
            checkBox.isChecked = task.isCompleted == 1
            setOnCheckedChangeListener(task, onUpdate)
            deleteButton.setOnClickListener {
                onDeleteClick.invoke(task)
            }
            editButton.setOnClickListener {
                onEditClick.invoke(task)
            }
        }
    }

    private fun setOnCheckedChangeListener(
        task: Task,
        onUpdate: ((Task) -> Unit)
    ) {
        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val updatedTask = task.copy(isCompleted = if (isChecked) 1 else 0)
            onUpdate.invoke(updatedTask)
        }

    }
    companion object{
        fun from(
            parent: ViewGroup
        ):TaskViewHolder{
            val binding = ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TaskViewHolder(binding)
        }
    }
}