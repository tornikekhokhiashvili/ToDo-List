package com.example.todolist.presentation.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Domain.model.Todo
import com.example.todolist.R
import com.example.todolist.databinding.ItemTodoBinding

class TodoViewHolder(
    private val binding: ItemTodoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        todo: Todo,
        onDeleteClicked: ((Todo) -> Unit)?,
        onEditClicked: ((Todo) -> Unit)?,
        onNavigateClick: ((Long) -> Unit)?
    ) {
        binding.apply {
            toDoListTv.text = todo.name
            listOptionsButton.setOnClickListener {
                showPopupMenu(todo, onDeleteClicked, onEditClicked)
            }
            root.setOnClickListener { onNavigateClick?.invoke(todo.id) }
        }
    }

    private fun showPopupMenu(
        todo: Todo, onDeleteClicked: ((Todo) -> Unit)?, onEditClicked: ((Todo) -> Unit)?
    ) {
        val dropDownMenu = PopupMenu(binding.root.context, binding.listOptionsButton)
        dropDownMenu.inflate(R.menu.todo_option_menu)
        dropDownMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    onDeleteClicked?.invoke(todo)
                    true
                }

                R.id.action_edit -> {
                    onEditClicked?.invoke(todo)
                    true
                }

                else -> false
            }
        }
        dropDownMenu.show()
    }

    companion object {
        fun from(
            parent: ViewGroup,
        ): TodoViewHolder {
            val binding = ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return TodoViewHolder(binding)
        }
    }
}
