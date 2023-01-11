package com.example.simpletodo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.TodoBinding


class TodoListAdapter(private val onItemClicked: (Todo) -> Unit, private val onClickButton: (Todo) -> Unit) :
    ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(DiffCallback) {

    class TodoViewHolder(private val binding: TodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val button = binding.doneButton

        fun bind(todo: Todo) {
            binding.apply {
                todoTitle.text = todo.title
                todoContent.text = todo.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }

        // bind text view to holder
        holder.bind(current)
        // select done or undone text on done_button
        if (current.done) {
            holder.button.text = holder.itemView.context.getString(R.string.button_done)
        } else {
            holder.button.text = holder.itemView.context.getString(R.string.button_undone)
        }

        holder.button.setOnClickListener {
            onClickButton(current)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }
}



