package com.example.todolist.viewtasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskDatabaseDao

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao): ViewModel() {

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks


    fun navigate(){
        _navigateToAddTasks.value = true
    }


}