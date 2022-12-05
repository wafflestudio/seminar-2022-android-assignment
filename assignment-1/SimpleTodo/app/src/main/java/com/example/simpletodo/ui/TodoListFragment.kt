package com.example.simpletodo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.SimpleTodoApplication
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

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

        val adapter = TodoListAdapter(
            onItemClicked = {
                val action = TodoListFragmentDirections.actionTodoListFragmentToTodoDetailFragment(it.id)
                this.findNavController().navigate(action)
            },
            onClickButton = { todo -> changeDoneButton(todo, todo.id) }
        )
        binding.recyclerView.adapter = adapter

        // show UNDONE todolist with no checked checkbox
        viewModel.undoneTodoList.observe(this.viewLifecycleOwner) { todos ->
            todos.let {
                Log.d("todoList", "todoList1: $it")
                adapter.submitList(it)
            }
        }

        // choose layout manager
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        // move to todo_adder screen
        binding.floatingActionButton.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoAdderFragment()
            this.findNavController().navigate(action)
        }

        binding.checkbox.setOnClickListener {
            //show all todolist without undone todolist with checked checkbox
            if (binding.checkbox.isChecked) {
                viewModel.todoList.observe(this.viewLifecycleOwner) { todos ->
                    todos.let {
                        Log.d("todoList", "todoList2: $it")
                        adapter.submitList(it)
                    }
                }
            }
            // if unchecked checkbox show UNDONE todolist
            else {
                viewModel.undoneTodoList.observe(this.viewLifecycleOwner) { todos ->
                    todos.let {
                        Log.d("todoList", "todoList3: $it")
                        adapter.submitList(it)
                    }
                }
            }
        }
    }
    // If DoneButton is clicked, change done db and text
    private fun changeDoneButton(todo: Todo, id: Long) {
        if (todo.done) {
            viewModel.doneToUndone(id)
        } else {
            viewModel.undoneToDone(id)
        }
    }
}
