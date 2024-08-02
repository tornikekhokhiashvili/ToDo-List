package com.example.todolist.presentation.todo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Domain.model.Todo
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTodoBinding
import com.example.todolist.presentation.todo.adapter.TodoAdapter
import com.example.todolist.presentation.viewModels.TodoViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var todoAdapter: TodoAdapter
    private val todoViewModel: TodoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setUpSettingsButton()
        setUpAddButton()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onDeleteClick = { todo ->
                todoViewModel.deleteTodo(todo)
            },
            onEditClick = { todo ->
                editTodo(todo)
            },
            onNavigateClick = { id ->
                navigateToTaskFragment(id)
            }
        )
//        binding.recyclerView.adapter = todoAdapter
//        binding.recyclerView.setHasFixedSize(true)
//        val itemDecoration = MaterialDividerItemDecoration(
//            requireContext(),
//            DividerItemDecoration.VERTICAL
//        ).apply {
//            isLastItemDecorated = false
//            setDividerColorResource(requireContext(), R.color.white)
//        }
//        binding.recyclerView.addItemDecoration(itemDecoration)

        // Set up RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
            setHasFixedSize(true)

            // Add item decoration
            val itemDecoration = MaterialDividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            ).apply {
                isLastItemDecorated = false
                setDividerColorResource(requireContext(), R.color.white)
            }
            addItemDecoration(itemDecoration)
        }
    }

    private fun setUpSettingsButton() {
        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_todoFragment_to_settingsFragment)
        }
    }

    private fun setUpAddButton() {
        binding.addButton.setOnClickListener {
            showAddListDialog()
        }
    }

    private fun showAddListDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Todo List")
        val input = EditText(requireContext())
        builder.setView(input)
        builder.setPositiveButton("Add") { _, _ ->
            val name = input.text.toString()
            if (name.isNotEmpty()) {
                todoViewModel.addTodo(name)
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
        Log.d("TodoFragment", "Daemata")
    }

    private fun editTodo(todo: Todo) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Todo List")
        val input = EditText(requireContext())
        builder.setView(input)
        builder.setPositiveButton("Edit") { _, _ ->
            val name = input.text.toString()
            todoViewModel.updateTodo(todo.copy(name = name))
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            todoViewModel.todo.collect {
                todoAdapter.submitList(it)
            }
        }
    }

    private fun navigateToTaskFragment(todoId: Long) {
        val todoName = getTodoNameById(todoId)
        findNavController().navigate(
            TodoFragmentDirections.actionTodoFragmentToTaskFragment(todoId, todoName)
        )
    }

    private fun getTodoNameById(id: Long): String {
        return todoViewModel.getNameById(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
