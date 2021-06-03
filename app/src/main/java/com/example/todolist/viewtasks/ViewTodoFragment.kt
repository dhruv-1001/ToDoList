package com.example.todolist.viewtasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.adapter.TodoListener
import com.example.todolist.databinding.FragmentViewTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewTodoFragment : Fragment() {

    private lateinit var binding: FragmentViewTodoBinding

    private val viewTodoViewModel: ViewTodoViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_view_todo, container, false)

        binding.viewTodoViewModel = viewTodoViewModel

        val todoAdapter = TodoAdapter(TodoListener {
                id -> viewTodoViewModel.onDelete(id)
        })

        binding.taskList.adapter = todoAdapter

        viewTodoViewModel.readData.observe(viewLifecycleOwner, {
            it?.let {
                todoAdapter.submitList(it)
            }
        })

        viewTodoViewModel.headingsPos.observe(viewLifecycleOwner, {
            updateTodo(it, todoAdapter)
        })

        viewTodoViewModel.navigateToAddTasks.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(R.id.action_viewTasksFragment_to_addTaskFragment)
            }
        })

        return binding.root
    }

    private fun updateTodo(filter: Int, todoAdapter: TodoAdapter){

        viewTodoViewModel.searchDatabase(filter).observe(viewLifecycleOwner, {
            it?.let {
                todoAdapter.submitList(it)
            }
        })

    }

}