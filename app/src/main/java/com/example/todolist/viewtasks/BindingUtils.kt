package com.example.todolist.viewtasks

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todolist.todo.Todo

@BindingAdapter("taskName")
fun TextView.taskName(item: Todo){
    item.let {
        text = item.taskName
    }
}

@BindingAdapter("taskDescription")
fun TextView.taskDescription(item: Todo){
    item.let{
        text = item.taskDescription
    }
}