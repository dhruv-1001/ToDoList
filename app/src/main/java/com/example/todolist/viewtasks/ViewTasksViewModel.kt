package com.example.todolist.viewtasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    init {
        getTasks()
    }

    fun navigate(){
        _navigateToAddTasks.value = true
    }

    fun getTasks(){
        
    }

}