package com.example.simpletodo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.SimpleTodoApplication
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.FragmentTodoListBinding

const val KEY_CHECKBOX = "checkbox_key"

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private var checkBox: Int = 1

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as SimpleTodoApplication).database.todoDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            checkBox = savedInstanceState.getInt(KEY_CHECKBOX, 1)
        }

        val adapter = TodoListAdapter(
            onItemClicked = {
                val action = TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(it.id)
                this.findNavController().navigate(action)
            },
            onClickButton = { todo -> changeDoneButton(todo) }
        )
        binding.recyclerView.adapter = adapter

        fun showTodoList() {
            //show all todolist without undone todolist with checked checkbox
            if (binding.checkbox.isChecked) {
                Log.d("checkbox", "checkbox: $checkBox")
                viewModel.todoListMerger.observe(this.viewLifecycleOwner) {
                    viewModel.todoList.let {adapter.submitList( it.value )}
                }
            }
            // if unchecked checkbox show UNDONE todolist
            else {
                Log.d("checkbox", "checkbox: $checkBox")
                viewModel.todoListMerger.observe(this.viewLifecycleOwner) {
                    viewModel.undoneTodoList.let {adapter.submitList( it.value )}
                }
            }
        }

        // drawing UI at first
        if (checkBox == 1) {
            binding.checkbox.isChecked = false
            showTodoList()
        } else {
            binding.checkbox.isChecked = true
            showTodoList()
        }

        // choose layout manager
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        // move to todo_adder screen
        binding.floatingActionButton.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoAdderFragment()
            this.findNavController().navigate(action)
        }

        binding.checkbox.setOnClickListener {
            checkBox *= -1
            showTodoList()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_CHECKBOX, checkBox)
    }

    // If DoneButton is clicked, change done db and text
    private fun changeDoneButton(todo: Todo) {
        if (todo.done) {
            viewModel.doneToUndone(todo.id)
        } else {
            viewModel.undoneToDone(todo.id)
        }
    }
}