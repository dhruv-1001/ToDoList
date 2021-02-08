package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Query ("SELECT*FROM all_tasks")
    fun getAllTasks(): LiveData<List<Task>>

}