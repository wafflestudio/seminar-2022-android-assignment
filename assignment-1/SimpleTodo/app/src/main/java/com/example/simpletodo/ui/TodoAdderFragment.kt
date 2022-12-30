package com.example.simpletodo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.databinding.FragmentTodoAdderBinding
import kotlinx.coroutines.launch

class TodoAdderFragment : Fragment() {
    private lateinit var binding: FragmentTodoAdderBinding

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
        binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener { addNewTodo() }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.inputTitle.editText?.text.toString(),
            binding.inputContent.editText?.text.toString()
        )
    }

    private fun addNewTodo() {
        if (isEntryValid()) {
            viewModel.addNewTodo(
                binding.inputTitle.editText?.text.toString(),
                binding.inputContent.editText?.text.toString()
            )
            Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_todoAdderFragment_to_todoListFragment)
        }
    }
}
