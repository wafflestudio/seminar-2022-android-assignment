package com.example.simpletodo.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodo.data.Todo
import com.example.simpletodo.databinding.ItemTodoListBinding

class TodoListAdapter(
    private val doneClickListener: (Todo) -> Unit
) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(DiffCallback) {

    class TodoViewHolder(
        private val binding: ItemTodoListBinding,
        private val doneClickListener: (Todo) -> Unit,
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.todo = todo
            binding.btnDone.setOnClickListener { doneClickListener(todo) }
            if (todo.isDone) {
                binding.btnDone.text = "Done"
            } else {
                binding.btnDone.text = "Perceived"
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        Log.d("hi0","created!")
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(
            ItemTodoListBinding.inflate(layoutInflater, parent, false),
            doneClickListener
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val getTodo = getItem(position)
        holder.bind(getTodo)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }

    }
}
