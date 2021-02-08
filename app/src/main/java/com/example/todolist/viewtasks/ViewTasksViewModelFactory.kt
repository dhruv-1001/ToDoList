package com.example.todolist.viewtasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.TaskDatabaseDao

class ViewTasksViewModelFactory(
        private val dataSource: TaskDatabaseDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewTasksViewModel::class.java)){
            return ViewTasksViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}