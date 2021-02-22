package com.example.todolist.viewtasks

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.todolist.database.Task

@BindingAdapter("taskName")
fun TextView.taskName(item: Task){
    item.let {
        text = item.taskName
    }
}