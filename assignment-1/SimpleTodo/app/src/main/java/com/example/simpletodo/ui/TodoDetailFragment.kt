package com.example.simpletodo.ui

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simpletodo.R
import com.example.simpletodo.SimpleTodoApplication
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.FragmentTodoDetailBinding
import com.example.simpletodo.databinding.FragmentTodoListBinding
import java.nio.file.Files.delete

class TodoDetailFragment : Fragment() {

    private val navigationArgs: TodoDetailFragmentArgs by navArgs()

    private var _binding: FragmentTodoDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as SimpleTodoApplication).database.todoDao()
        )
    }
    lateinit var todo: Todo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.todoId
        viewModel.retrieveTodo(id).observe(this.viewLifecycleOwner) {
            todo = it
            bind(it)
        }

        binding.saveAction.setOnClickListener {
            updateTodo()
            Toast.makeText(requireActivity(), R.string.toast_message_save, Toast.LENGTH_SHORT).show()
        }

        binding.deleteAction.setOnClickListener {
            deleteTodo()
            Toast.makeText(requireActivity(), R.string.toast_message_delete, Toast.LENGTH_SHORT).show()
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

    private fun bind(todo: Todo) {
        binding.apply {
            todoTitle.setText(todo.title, TextView.BufferType.SPANNABLE)
            todoContent.setText(todo.content, TextView.BufferType.SPANNABLE)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.todoTitle.text.toString(),
            binding.todoContent.text.toString(),
        )
    }

    private fun updateTodo() {
        if (isEntryValid()) {
            viewModel.saveUpdatedTodo(
                this.navigationArgs.todoId,
                this.binding.todoTitle.text.toString(),
                this.binding.todoContent.text.toString(),
                this.todo.done
            )
            val action = TodoDetailFragmentDirections.actionTodoDetailFragmentToTodoListFragment()
            findNavController().navigate(action)
        }
    }

    private  fun deleteTodo() {
        if (isEntryValid()) {
            viewModel.deleteTodo(todo)
            val action = TodoDetailFragmentDirections.actionTodoDetailFragmentToTodoListFragment()
            findNavController().navigate(action)
        }
    }
}