package com.example.todolist.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_task, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = AddTaskViewModelFactory(dataSource)
        val addTaskViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddTaskViewModel::class.java)

        binding.addTaskViewModel = addTaskViewModel
        addTaskViewModel.navigateToViewTasks.observe(viewLifecycleOwner, Observer {
            if (it == true){
                findNavController().navigate(R.id.action_addTaskFragment_to_viewTasksFragment)
            }
        })
        return binding.root
    }
}