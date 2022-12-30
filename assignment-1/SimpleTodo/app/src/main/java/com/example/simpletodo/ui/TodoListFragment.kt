package com.example.simpletodo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.databinding.FragmentTodoListBinding
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding

    private lateinit var recyclerView: RecyclerView

    var job: Job? = null

    private val viewModel: TodoViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST") if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                    return TodoViewModel(TodoDatabase.getDatabase(requireContext()).todoDao()) as T
                }
                return super.create(modelClass)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addItemButton.setOnClickListener { toTodoAdderFragment() }
        binding.showDoneCheckbox.setOnClickListener { setShowDone() }
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val todoListAdapter = TodoListAdapter {todo: Todo? -> viewModel.updateTodo(todo) }
        recyclerView.adapter = todoListAdapter
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setShowDone()
    }

    fun setShowDone() {
        val todoListAdapter = recyclerView.adapter as TodoListAdapter
        job?.cancel()
        if(binding.showDoneCheckbox.isChecked) {
            job = lifecycle.coroutineScope.launch() {
                viewModel.getAll().collect() {
                    todoListAdapter.submitList(it)
                }
            }
        }
        else {
            job = lifecycle.coroutineScope.launch() {
                viewModel.getOngoing().collect() {
                    todoListAdapter.submitList(it)
                }
            }
        }
    }

    fun toTodoAdderFragment() {
        findNavController().navigate(R.id.action_todoListFragment_to_todoAdderFragment)
    }
}
