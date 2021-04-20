package com.example.todolist.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentAddTaskBinding
import com.example.todolist.todo.Todo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val addTodoVIewModel: AddTodoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_task, container, false
        )

        binding.addTodoViewModel = addTodoVIewModel

        addTodoVIewModel.navigateToViewTasks.observe(viewLifecycleOwner, Observer {
            if (it == true){
                findNavController().navigate(R.id.action_addTaskFragment_to_viewTasksFragment)
            }
        })

        return binding.root
    }
}