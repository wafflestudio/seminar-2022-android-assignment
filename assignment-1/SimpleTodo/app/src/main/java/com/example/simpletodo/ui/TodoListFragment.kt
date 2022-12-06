package com.example.simpletodo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.simpletodo.R
import com.example.simpletodo.SimpleTodoApplication
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.FragmentTodoListBinding
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by activityViewModels() {
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

        val adapter = TodoListAdapter { viewModel.updateIsDone(it) }

        viewModel.currentTodo.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            recyclerView.adapter = adapter
            checkboxDone.setOnClickListener { checkboxDoneClicked() }
            addTodoButton.setOnClickListener { addTodoBtnClicked() }
        }
    }

    private fun checkboxDoneClicked() {
        val isCheckboxChecked : Boolean = binding.checkboxDone.isChecked
        viewModel.setIsDoneShow(isCheckboxChecked)
    }

    private fun addTodoBtnClicked() {
        findNavController().navigate(R.id.action_todoListFragment_to_todoAdderFragment)
    }
}




/*
     viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
             viewModel.currentTodo.collect {
                 adapter.submitList(it)
             }
         }
     }
      */