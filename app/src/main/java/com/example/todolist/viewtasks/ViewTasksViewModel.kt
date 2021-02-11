package com.example.todolist.viewtasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskDatabaseDao
import com.example.todolist.formatTasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao, private val application: Application): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    val allTasks = dataSource.getAllTasks()

    val taskString = Transformations.map(allTasks){ allTasks ->
        formatTasks(allTasks, application.resources)
    }

    init {
//        getTasks()
    }

    fun navigate(){
        _navigateToAddTasks.value = true
    }



}