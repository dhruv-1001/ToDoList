package com.example.todolist.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.todo.Todo
import com.example.todolist.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
): ViewModel() {

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
                val todo = Todo()
                todo.taskName = editTextContentTaskName.value.toString()
                todo.taskName = todo.taskName.toLowerCase()
                todo.taskName = todo.taskName.capitalize()
                todo.taskDescription = editTextContentTaskDescription.value.toString()
                todo.taskDescription = todo.taskDescription.toLowerCase()
                todo.taskDescription = todo.taskDescription.capitalize()
                todo.taskDay = day.value!!
                todo.taskMonth = month.value?.plus(1)!!
                todo.taskYear = year.value!!
                todoRepository.insertData(todo)
            }
            _navigateToViewTasks.value = true
        }
    }

    fun onCancelClick(){
        _navigateToViewTasks.value = true
    }

}