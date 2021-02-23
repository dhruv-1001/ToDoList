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
    val year: MutableLiveData<Int> = MutableLiveData(Calendar.getInstance().get(Calendar.YEAR))
    val month: MutableLiveData<Int> = MutableLiveData(Calendar.getInstance().get(Calendar.MONTH))
    val day: MutableLiveData<Int> = MutableLiveData(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDateChanged(Year: Int, Month: Int, Day: Int){
        year.value = Year
        month.value = Month
        day.value = Day
    }

    fun onSaveClick(){
        uiScope.launch{
            withContext(Dispatchers.IO){
                val newTask = Task()
                newTask.taskName = editTextContentTaskName.value.toString()
                newTask.taskDescription = editTextContentTaskDescription.value.toString()
                newTask.taskTimeDay = day.value!!
                newTask.taskTimeMonth = month.value?.plus(1)!!
                newTask.taskTimeYear = year.value!!
                database.insert(newTask)
            }
            _navigateToViewTasks.value = true
        }
    }

    fun onCancelClick(){
        _navigateToViewTasks.value = true
    }

}