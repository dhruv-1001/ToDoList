package com.example.todolist.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddTodoBinding
    private val addTodoVIewModel: AddTodoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_todo, container, false
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