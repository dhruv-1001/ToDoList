package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_tasks")
data class Task (

    @PrimaryKey(autoGenerate = true)
    val taskKey: Long = 0L,

    @ColumnInfo(name = "task_name")
    var taskName: String = ""

)