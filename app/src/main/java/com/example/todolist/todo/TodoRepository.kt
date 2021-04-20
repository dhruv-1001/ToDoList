package com.example.todolist.todo

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {


    val cal = Calendar.getInstance()
    val day = cal.get(Calendar.DAY_OF_MONTH)
    val month = cal.get(Calendar.MONTH) + 1
    val year = cal.get(Calendar.YEAR)

    suspend fun insertData(todo: Todo){
        todoDao.insertData(todo)
    }

    fun readData(): Flow<List<Todo>>{
        return todoDao.readData()
    }

    fun readLateData(): Flow<List<Todo>>{
        return todoDao.readLateData(day, month, year)
    }

    fun readTodayData(): Flow<List<Todo>>{
        return todoDao.readTodayData(day, month, year)
    }

    fun readUpcomingData(): Flow<List<Todo>>{
        return todoDao.readUpcomingData(day, month, year)
    }

    fun deleteData(id: Int){
        todoDao.deleteTask(id)
    }

}