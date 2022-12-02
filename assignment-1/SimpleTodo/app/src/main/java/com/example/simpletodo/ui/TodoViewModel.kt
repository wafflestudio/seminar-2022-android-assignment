package com.example.simpletodo.ui

import androidx.lifecycle.ViewModel
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDao
import kotlinx.coroutines.flow.*

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {
    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }
}
