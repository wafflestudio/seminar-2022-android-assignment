package com.example.simpletodo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.R
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.ItemTodoListBinding


class TodoListAdapter(private val onItemClicked: (Todo) -> Unit)
    : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClicked)
    }

    class TodoViewHolder(private var binding: ItemTodoListBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo, clicked: (Todo) -> Unit) {
            binding.apply{
                todoTitle.text = todo.title
                todoContent.text = todo.content
                todoStatusButton.text = when(todo.isDone) {
                    true -> context.getString(R.string.status_done)
                    else -> context.getString(R.string.status_undone)
                }
                todoStatusButton.setOnClickListener{
                    clicked(todo)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areContentsTheSame(oldTodo: Todo, newTodo: Todo): Boolean {
                return oldTodo == newTodo
            }

            override fun areItemsTheSame(oldItem: Todo, newTodo: Todo): Boolean {
                return oldItem.id == newTodo.id
            }
        }
    }
}
