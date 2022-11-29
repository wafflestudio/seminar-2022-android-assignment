package com.example.simpletodo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.databinding.FragmentTodoListBinding
import com.example.simpletodo.databinding.ItemTodoListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v("TodoListFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val adapter = TodoListAdapter{
            toggleTodo(it)
        }
        viewModel.allTodos.observe(this.viewLifecycleOwner){ items ->
            items.let{
                adapter.submitList(it)
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floating.setOnClickListener{
            val action = TodoListFragmentDirections.actionTodoListFragmentToTodoAdderFragment()
            this.findNavController().navigate(action)
        }
        binding.checkbox.isChecked = true
        binding.checkbox.setOnClickListener {
            checkboxToggle()
        }
    }

    private fun checkboxToggle(){
        val adapter = TodoListAdapter{
            toggleTodo(it)
        }
        if(binding.checkbox.isChecked){
            viewModel.allTodos.observe(this.viewLifecycleOwner){ items ->
                items.let{
                    adapter.submitList(it)
                }
            }
        }
        else{
            viewModel.unDoneTodos.observe(this.viewLifecycleOwner){ items ->
                items.let{
                    adapter.submitList(it)
                }
            }
        }
        binding.recyclerView.adapter = adapter
    }

    private fun toggleTodo(todo: Todo){
        viewModel.toggleTodo(todo)
    }
}
