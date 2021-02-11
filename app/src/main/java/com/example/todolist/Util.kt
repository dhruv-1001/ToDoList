package com.example.todolist

import android.content.res.Resources
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.Task

fun formatTasks(tasks: List<Task>, resources: Resources): Spanned{
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.all_tasks_heading))
        tasks.forEach {
            append("${it.taskName}<br>")
        }
    }

    return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)

}
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)