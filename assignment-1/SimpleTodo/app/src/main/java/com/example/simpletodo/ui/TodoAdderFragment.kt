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

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.newTodoTitle.text.toString(),
            binding.newTodoContent.text.toString()
        )
    }

    private fun addNewTodo() {
        if(isEntryValid()) {
            viewModel.addNewTodo(
                binding.newTodoTitle.text.toString(),
                binding.newTodoContent.text.toString()
            )
        }
        val myToast = Toast.makeText(context, R.string.add_new_item, Toast.LENGTH_SHORT)
        myToast.show()
        val action = TodoAdderFragmentDirections.actionTodoAdderFragmentToTodoListFragment()
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitTodo.setOnClickListener {
            addNewTodo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}
