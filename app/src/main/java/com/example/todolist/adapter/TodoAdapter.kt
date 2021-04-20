package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListItemTaskBinding
import com.example.todolist.todo.Todo


class TodoAdapter(
    val clickListener: TodoListener
): ListAdapter<Todo, TodoAdapter.ViewHolder>(ViewTodoDiffCallBack()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        val binding: ListItemTaskBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Todo, clickListener: TodoListener){
            binding.todo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTaskBinding.inflate(
                    layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

}

class TodoListener(val clickListener: (id: Int) -> Unit){
    fun onClick(todo: Todo) = clickListener(todo.id)
}

class ViewTodoDiffCallBack: DiffUtil.ItemCallback<Todo>(){
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }

}