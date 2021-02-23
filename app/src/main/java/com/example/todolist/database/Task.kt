package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "all_tasks")
data class Task (

    @PrimaryKey(autoGenerate = true)
    val taskKey: Long = 0L,

    @ColumnInfo(name = "task_name")
    var taskName: String = "",

    @ColumnInfo(name = "task_description")
    var taskDescription: String = "",

    @ColumnInfo(name = "task_time_day")
    var taskTimeDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),

    @ColumnInfo(name = "task_time_month")
    var taskTimeMonth: Int = Calendar.getInstance().get(Calendar.MONTH)+1,

    @ColumnInfo(name = "task_time_year")
    var taskTimeYear: Int = Calendar.getInstance().get(Calendar.YEAR)

)