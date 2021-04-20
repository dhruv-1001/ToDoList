package com.example.todolist.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(todo: Todo)

    @Query("SELECT*FROM todo_database")
    fun readData(): Flow<List<Todo>>

    @Query("SELECT*FROM todo_database WHERE (taskDay = :day AND taskMonth = :month AND taskYear = :year)")
    fun readTodayData(day: Int, month: Int, year: Int): Flow<List<Todo>>

    @Query("SELECT*FROM todo_database WHERE (taskDay < :day AND taskMonth = :month AND taskYear =:year) OR (taskMonth < :month AND taskYear =:year) OR (taskYear < :year)")
    fun readLateData(day: Int, month: Int, year: Int): Flow<List<Todo>>

    @Query("SELECT*FROM todo_database WHERE (taskDay > :day AND taskMonth = :month AND taskYear =:year) OR (taskMonth > :month AND taskYear =:year) OR (taskYear > :year)")
    fun readUpcomingData(day: Int, month: Int, year: Int): Flow<List<Todo>>

    @Query("DELETE FROM todo_database WHERE id =:id")
    fun deleteTask(id: Int)

}