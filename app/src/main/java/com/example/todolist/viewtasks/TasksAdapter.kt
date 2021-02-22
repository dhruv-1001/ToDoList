package com.example.todolist.viewtasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.Task
import com.example.todolist.databinding.ListItemTaskBinding

class TasksAdapter (val clickListener: TaskListener): ListAdapter<Task, TasksAdapter.ViewHolder>(ViewTasksDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val  binding: ListItemTaskBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task, clickListener: TaskListener) {
            binding.task = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTaskBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ViewTasksDiffCallBack: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskKey == newItem.taskKey
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskListener(val clickListener: (taskKey: Long) -> Unit){
    fun onClick(task: Task) = clickListener(task.taskKey)
}
