package com.example.todolist.presentation.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todolist.Domain.model.Task
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTaskBinding
import com.example.todolist.presentation.task.adapter.TaskAdapter
import com.example.todolist.presentation.viewModels.TaskViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private val args: TaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle()
        setUpRecyclerView()
        observeViewModel()
        setOnClickListeners()
        taskViewModel.getTasks(args.listId)
    }

    private fun setUpToolbarTitle() {
        binding.toolbar.title = args.todoName
    }

    private fun setUpRecyclerView() {
        taskAdapter = TaskAdapter(onDeleteClick = { task ->
            showDeleteTaskDialog(task)
        }, onEditClick = { task ->
            showEditTaskDialog(task)
        }, onUpdate = { task ->
            taskViewModel.updateTask(task)
        })
        binding.taskRecyclerView.adapter = taskAdapter
        binding.taskRecyclerView.setHasFixedSize(true)
        val dividerItemDecoration = MaterialDividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL,
        ).apply {
            isLastItemDecorated = false
            setDividerColorResource(requireContext(), R.color.white)
        }
        binding.taskRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun setOnClickListeners() {
        binding.addButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.tasks.collect { tasks ->
                taskAdapter.submitList(tasks)
            }
        }
    }

    private fun showAddTaskDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add ToDo Item")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("ADD") { dialog, _ ->
            val enteredText = input.text.toString()
            val todoId = args.listId

            taskViewModel.addTask(
                Task(
                    name = enteredText, todoId = todoId
                )
            )
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showEditTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update ToDo Task")
        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedText = input.text.toString()

            taskViewModel.updateTask(
                task.copy(
                    name = updatedText
                )
            )
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showDeleteTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete ToDo Task")

        builder.setPositiveButton("Delete") { _, _ ->
            taskViewModel.deleteTask(task)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}