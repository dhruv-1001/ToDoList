package com.example.todolist.viewtasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todolist.todo.Todo
import com.example.todolist.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ViewTodoViewModel @Inject constructor(
    private var todoRepository: TodoRepository
): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _navigateToAddTasks =  MutableLiveData<Boolean?>()
    val navigateToAddTasks: LiveData<Boolean?> get() = _navigateToAddTasks

    val headings = arrayListOf("All Tasks", "Today", "Late", "Upcoming")
    val headingsPos = MutableLiveData<Int>()

    val readData = todoRepository.readData().asLiveData()

    fun searchDatabase(filter: Int): LiveData<List<Todo>>{
        return if (filter == 0) todoRepository.readData().asLiveData()
        else if (filter == 1) todoRepository.readTodayData().asLiveData()
        else if (filter == 2) todoRepository.readLateData().asLiveData()
        else todoRepository.readUpcomingData().asLiveData()
    }

    fun navigate(){
        _navigateToAddTasks.value = true
    }

    private suspend fun deleteTask(id: Int) {
        withContext(Dispatchers.IO) {
            todoRepository.deleteData(id)
        }
    }

    fun onDelete(id: Int){
        viewModelScope.launch {
            deleteTask(id)
        }
    }

}