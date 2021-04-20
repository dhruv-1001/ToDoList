package com.example.todolist.viewtasks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todolist.R
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentViewTasksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewTasksFragment : Fragment() {

    private lateinit var binding: FragmentViewTasksBinding

//    private val viewTodoViewModel: ViewTodoViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_view_tasks, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = ViewTasksViewModelFactory(dataSource, application)
        val viewTasksViewModel  = ViewModelProviders.of(this, viewModelFactory)
                .get(ViewTasksViewModel::class.java)
        binding.viewTasksViewModel = viewTasksViewModel
        val adapter = TasksAdapter(TaskListener {
            taskKey -> viewTasksViewModel.onDelete(taskKey)
        })

        binding.taskList.adapter = adapter

        viewTasksViewModel.allTasks.observe(viewLifecycleOwner, {
            it.let {
                adapter.submitList(it)
            }
        })

        viewTasksViewModel.headingsPos.observe(viewLifecycleOwner, {
            it.let{
                viewTasksViewModel.onUpdateList()
            }
        })

        viewTasksViewModel.navigateToAddTasks.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(R.id.action_viewTasksFragment_to_addTaskFragment)
            }
        })

        return binding.root
    }

}