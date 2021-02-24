package com.example.todolist.viewtasks

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.TaskDatabaseDao
import com.example.todolist.formatTasks
import kotlinx.coroutines.*
import java.util.*

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao, private val application: Application): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    val cal = Calendar.getInstance()

    val lateTasks = dataSource.getLateTasks(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
    val todayTasks = dataSource.getTodayTasks(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))
    val upcomingTasks = dataSource.getUpdateTasks(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR))

    fun navigate(){
        _navigateToAddTasks.value = true
    }

    private suspend fun deleteTask(taskKey: Long) {
        withContext(Dispatchers.IO) {
            dataSource.deleteTask(taskKey)
        }
    }

    fun onDelete(taskKey: Long){
        viewModelScope.launch {
            deleteTask(taskKey)
        }
    }

}