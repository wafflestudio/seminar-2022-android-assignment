package com.example.simpletodo.ui

import androidx.lifecycle.*
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {
    val todoList: LiveData<List<Todo>> = todoDao.getTodoList().asLiveData()

    fun retrieveTodo(id: Long) : LiveData<Todo>{
        return todoDao.getTodo(id).asLiveData()
    }

    fun addTodo(title: String, content: String, done: Boolean = false) {
        val todo = Todo(
            title = title,
            content = content,
            done = done
        )
        viewModelScope.launch {
            todoDao.insertTodo(todo)
        }
    }

    fun updateTodo(title: String, content: String, done: Boolean = false) {
        val todo = Todo(
            title = title,
            content = content,
            done = done
        )
        viewModelScope.launch {
            todoDao.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(todo)
        }
    }

    fun undoneToDone(id: Long) {
        viewModelScope.launch {
            todoDao.undoneToDone(id)
        }
    }

    fun doneToUndone(id: Long) {
        viewModelScope.launch {
            todoDao.doneToUndone(id)
        }
    }

    fun isEntryValid(title: String, content: String): Boolean {
        return title.isNotBlank() && content.isNotBlank()
    }
}

class TodoViewModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
