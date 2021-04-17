package com.example.todolist.viewtasks

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.*
import java.util.*

class ViewTasksViewModel(private val dataSource: TaskDatabaseDao, private val application: Application): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()

    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val month = cal.get(Calendar.MONTH) + 1
    val year = cal.get(Calendar.YEAR)

    var allTasks = dataSource.getAllTasks()

    val headings = arrayListOf("All Tasks", "Today", "Late", "Upcoming")
    val headingsPos = MutableLiveData<Int>()

    private suspend fun updateList(){
        withContext(Dispatchers.IO){
            allTasks = if (headingsPos.value == 0) dataSource.getAllTasks()
            else if (headingsPos.value == 1) dataSource.getTodayTasks(day, month, year)
            else if (headingsPos.value == 2) dataSource.getLateTasks(day, month, year)
            else dataSource.getUpcomingTasks(day, month, year)
        }
    }

    fun onUpdateList(){
        viewModelScope.launch {
            updateList()
            Log.i("Head Position", "${headingsPos.value}")
        }
    }

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