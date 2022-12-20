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

    private val _isDoneShow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _currentTodo : Flow<List<Todo>> = _isDoneShow.flatMapLatest { value -> todoDao.getUserTodo(value) }
    val currentTodo : LiveData<List<Todo>> = _currentTodo.asLiveData()

    fun insertInputTodo(title: String, content: String) {
        insertTodo(Todo(title = title, content = content, isDone = false))
    }

    private fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertTodo(todo)
        }
    }

    fun updateIsDone(todo: Todo) {
        val copy = if(todo.isDone) {
            todo.copy(isDone = false)
        } else {
            todo.copy(isDone = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateTodo(copy)
        }
    }

    fun setIsDoneShow(checked: Boolean) {
        _isDoneShow.value = checked
    }

    fun isInputValid(title: String, content: String): Boolean {
        return !(title.isBlank() || content.isBlank())
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
