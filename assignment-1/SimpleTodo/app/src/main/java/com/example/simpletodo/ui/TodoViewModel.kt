package com.example.simpletodo.ui

import android.content.ClipData
import android.util.Log
import androidx.lifecycle.*
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {
    val todoList: LiveData<List<Todo>> = todoDao.getTodoList().asLiveData()
    val undoneTodoList: LiveData<List<Todo>> = todoDao.getUndoneList().asLiveData()

//    var twinTodoListValid: MediatorLiveData<Boolean> = MediatorLiveData()

//    twinTodoListValid.addSource(todoList, {
//        twinTodoListValid.value = todoList.value?.isNotEmpty() == true && this?.isNotEmpty() ?: false
//    })
//
//    twinTodoListValid.addSource(undoneTodoList, {
//        twinTodoListValid.value = todoList.value?.isNotEmpty() == true && this?.isNotEmpty() ?: false
//    })

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

    private fun updateTodo(todo: Todo) {
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
        Log.d("button", "fun activated")
    }

    fun doneToUndone(id: Long) {
        viewModelScope.launch {
            todoDao.doneToUndone(id)
        }
    }

    fun isEntryValid(title: String, content: String): Boolean {
        return title.isNotBlank() && content.isNotBlank()
    }

    private fun getUpdatedTodoEntry(
        id: Long,
        title: String,
        content: String,
        done: Boolean
    ): Todo {
        return Todo(
            id = id,
            title = title,
            content = content,
            done = done
        )
    }

    fun saveUpdatedTodo(
        id: Long,
        title: String,
        content: String,
        done: Boolean
    ) {
        val updatedTodo = getUpdatedTodoEntry(id, title, content, done)
        updateTodo(updatedTodo)
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
