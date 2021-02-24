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

    @Query("SELECT*FROM all_tasks where (task_time_day = :day AND task_time_month = :month AND task_time_year = :year)")
    fun getTodayTasks(day: Int, month: Int, year: Int): LiveData<List<Task>>

    @Query("SELECT*FROM all_tasks where (task_time_day < :day AND task_time_month = :month AND task_time_year =:year) OR (task_time_month < :month AND task_time_year =:year) OR (task_time_year < :year)")
    fun getLateTasks(day: Int, month: Int, year: Int): LiveData<List<Task>>

    @Query("SELECT*FROM all_tasks where (task_time_day > :day AND task_time_month = :month AND task_time_year =:year) OR (task_time_month > :month AND task_time_year =:year) OR (task_time_year > :year)")
    fun getUpdateTasks(day: Int, month: Int, year: Int) : LiveData<List<Task>>

    @Query("DELETE FROM all_tasks where taskKey = :key")
    fun deleteTask(key: Long)

}