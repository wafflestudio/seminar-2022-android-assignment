package com.example.simpletodo.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDatabase
import com.example.simpletodo.databinding.TodoItemBinding


// class TodoListAdapter() : ListAdapter<Todo, TodoListAdapter.ViewHolder>()



class TodoListAdapter(private val onBtnClicked: (Todo?) -> Unit): ListAdapter<Todo, TodoListAdapter.TodoListViewHolder>(DiffCallback) {

    class TodoListViewHolder(private var binding: TodoItemBinding, private val onBtnClicked: (Todo?) -> Unit): RecyclerView.ViewHolder(binding.root) {
        private val _todo = MutableLiveData<Todo>()
        val todo: LiveData<Todo> = _todo

        fun bind(value: Todo) {
            _todo.value = value
            binding.todoViewHolder = this
            binding.doneButton.setOnClickListener {
                _todo.value = _todo.value?.copy(done=!_todo.value?.done!!)
                onBtnClicked(_todo.value)
            }
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val viewHolder = TodoListViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onBtnClicked
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}