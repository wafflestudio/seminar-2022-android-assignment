package com.example.simpletodo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.insertTodo(todo)
        }
    }

    private fun getNewTodoEntry (title: String, content: String): Todo {
        return Todo(
            title = title,
            content = content,
            createdAt = System.currentTimeMillis(),
            done = false
        )
    }

    fun isEntryValid(title: String, content: String): Boolean {
        if(title.isBlank() || content.isBlank()) {
            return false
        }
        return true
    }

    fun addNewTodo(title: String, content: String) {
        val newTodo = getNewTodoEntry(title, content)
        insertTodo(newTodo)
    }

    fun getAll(): Flow<List<Todo>> {
        return todoDao.getAll()
    }

    fun getOngoing(): Flow<List<Todo>> {
        return todoDao.getOngoingTodos()
    }

    fun updateTodo(todo: Todo?) {
        if(todo != null) {
            viewModelScope.launch {
                todoDao.update(todo)
            }
        }
    }
}
