package com.example.todolist.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.*
import java.util.*

class AddTaskViewModel(
    private val database: TaskDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)


    private var _navigateToViewTasks =  MutableLiveData<Boolean?>()
    val navigateToViewTasks: LiveData<Boolean?>
        get() = _navigateToViewTasks



    val editTextContentTaskName = MutableLiveData<String>()
    val editTextContentTaskDescription = MutableLiveData<String>()
    var datePickerContentDay = MutableLiveData<String>().apply { postValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())}
    var datePickerContentMonth = MutableLiveData<String>().apply { postValue((Calendar.getInstance().get(Calendar.MONTH)+1).toString())}
    var datePickerContentYear = MutableLiveData<String>().apply { postValue(Calendar.getInstance().get(Calendar.YEAR).toString()) }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onAddingTask(){
        uiScope.launch{
            withContext(Dispatchers.IO){
                val newTask = Task()
                newTask.taskName = editTextContentTaskName.value.toString()
                newTask.taskDescription = editTextContentTaskDescription.value.toString()
                newTask.taskTimeDay = datePickerContentDay.value?.toInt()!!
                newTask.taskTimeMonth = datePickerContentMonth.value?.toInt()!!
                newTask.taskTimeYear = datePickerContentYear.value?.toInt()!!
                database.insert(newTask)
            }
            _navigateToViewTasks.value = true
        }
    }
}