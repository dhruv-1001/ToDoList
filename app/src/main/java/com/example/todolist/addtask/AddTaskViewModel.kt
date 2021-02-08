package com.example.todolist.addtask

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.*

class AddTaskViewModel(
    private val database: TaskDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.Main + viewModelJob)


    private var _navigateToViewTasks =  MutableLiveData<Boolean?>()

    val navigateToViewTasks: LiveData<Boolean?>
        get() = _navigateToViewTasks


    val editTextContent = MutableLiveData<String>()


    fun onAddingTask(){
        uiScope.launch{
            withContext(Dispatchers.IO){
                val newTask = Task()
                newTask.taskName = editTextContent.value.toString()
                database.insert(newTask)
            }
            _navigateToViewTasks.value = true
        }
    }

}