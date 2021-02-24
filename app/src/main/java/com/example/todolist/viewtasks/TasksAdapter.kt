package com.example.todolist.viewtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.databinding.ListItemTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class TasksAdapter (val clickListener: TaskListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(ViewTasksDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolder -> {
                var item = getItem(position) as DataItem.TaskItem
                holder.bind(item.task, clickListener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.TaskItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: List<Task>?){
        adapterScope.launch {
            val items = when (list){
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.TaskItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view){
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
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

class ViewTasksDiffCallBack: DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class TaskListener(val clickListener: (taskKey: Long) -> Unit){
    fun onClick(task: Task) = clickListener(task.taskKey)
}

sealed class DataItem{
    data class TaskItem(val task: Task): DataItem(){
        override val id = task.taskKey
    }
    object Header: DataItem(){
        override val id = Long.MIN_VALUE
    }
    abstract val id: Long
}

