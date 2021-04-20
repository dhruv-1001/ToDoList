package com.example.todolist.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_database")
data class Todo(
    var taskName: String = "",
    var taskDescription: String = "",
    var taskDay: Int = -1,
    var taskMonth: Int = -1,
    var taskYear: Int = -1
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
