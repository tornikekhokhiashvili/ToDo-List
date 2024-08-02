package com.example.todolist.presentation.todo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.Domain.model.Todo

class TodoDiffUtil:DiffUtil.ItemCallback<Todo>(){
    override fun areItemsTheSame(oldItem:Todo,newItem:Todo):Boolean{
        return oldItem.id==newItem.id
    }
    override fun areContentsTheSame(oldItem:Todo,newItem:Todo):Boolean{
        return oldItem==newItem
    }
}
