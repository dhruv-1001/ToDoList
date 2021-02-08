package com.example.todolist.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.TaskDatabaseDao
import java.lang.IllegalArgumentException

class AddTaskViewModelFactory(
    private val dataSource: TaskDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
            return AddTaskViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}