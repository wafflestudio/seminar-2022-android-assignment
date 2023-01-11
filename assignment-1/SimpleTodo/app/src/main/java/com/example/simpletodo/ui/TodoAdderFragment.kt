package com.example.simpletodo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simpletodo.R
import com.example.simpletodo.SimpleTodoApplication
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.databinding.FragmentTodoAdderBinding
import kotlinx.coroutines.launch

class TodoAdderFragment : Fragment() {

    private var _binding: FragmentTodoAdderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as SimpleTodoApplication).database.todoDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoAdderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewTodo()
            Toast.makeText(requireActivity(), R.string.toast_message_add, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.todoTitle.text.toString(),
            binding.todoContent.text.toString(),
        )
    }

    private fun addNewTodo() {
        if (isEntryValid()) {
            viewModel.addTodo(
                title = binding.todoTitle.text.toString(),
                content = binding.todoContent.text.toString()
            )
            val action = TodoAdderFragmentDirections.actionTodoAdderFragmentToTodoListFragment()
            findNavController().navigate(action)
        }
    }
}
