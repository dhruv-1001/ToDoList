package com.example.todolist.viewtasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskDatabaseDao
import com.example.todolist.formatTasks
import kotlinx.coroutines.*

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao, private val application: Application): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    val allTasks = dataSource.getAllTasks()

    fun navigate(){
        _navigateToAddTasks.value = true
    }
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            dataSource.clear()
        }
    }

    private suspend fun deleteTask(taskKey: Long) {
        withContext(Dispatchers.IO) {
            dataSource.deleteTask(taskKey)
        }
    }

    fun onClear(){
        viewModelScope.launch {
            clear()
        }
    }

    fun onDelete(taskKey: Long){
        viewModelScope.launch {
            deleteTask(taskKey)
        }
    }

}