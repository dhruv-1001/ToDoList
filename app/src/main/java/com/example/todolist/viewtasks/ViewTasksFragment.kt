package com.example.todolist.viewtasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.addtask.AddTaskViewModel
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentViewTasksBinding

class ViewTasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentViewTasksBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_view_tasks, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = ViewTasksViewModelFactory(dataSource)
        val viewTasksViewModel  = ViewModelProviders.of(this, viewModelFactory)
                .get(ViewTasksViewModel::class.java)
        binding.viewTasksViewModel = viewTasksViewModel

        viewTasksViewModel.navigateToAddTasks.observe(viewLifecycleOwner, Observer {
            if (it == true){
                findNavController().navigate(R.id.action_viewTasksFragment_to_addTaskFragment)
            }
        })

        return binding.root
    }
}