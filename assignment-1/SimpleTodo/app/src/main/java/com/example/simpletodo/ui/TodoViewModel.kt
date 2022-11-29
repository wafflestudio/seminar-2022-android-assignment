package com.example.simpletodo.ui

import androidx.lifecycle.*
import com.example.simpletodo.data.Todo
import com.example.simpletodo.data.TodoDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class TodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {
    // 모든 투두의 리스트 불러오기
    val allTodos: LiveData<List<Todo>> = todoDao.getTodos().asLiveData()
    // 완료되지 않은 투두의 리스트
    val unDoneTodos: LiveData<List<Todo>> = todoDao.getUndoneTodos().asLiveData()

    private var cnt = 1

    // TodoDao를 통해 새 투두 삽입
    private fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.insertTodo(todo)
        }
    }
    // 이미 있는 투두를 업데이트
    private fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.update(todo)
        }
    }

    // 새로 만들려는 투두가 올바른지 확인 (addNewTodo를 외부에서 호출하기 전 확인)
    fun isEntryValid(title: String, content: String): Boolean{
        if(title.isBlank() || content.isBlank())    return false
        return true
    }
    // 새 투두 만들기
    fun addNewTodo(title: String, content: String) {
        // 생성 시간 오름차순으로 정렬 위해 시스템 시간을 createdAt에 입력
        // 투두 생성 시 완료 여부는 false
        val newTodo = Todo(
            createdAt = System.currentTimeMillis(),
            title = title,
            content = content,
            isDone = false
        )
        insertTodo(newTodo)
    }

    // 투두의 완료 여부를 토글하여 업데이트
    fun toggleTodo (todo: Todo) {
        val updatedTodo = Todo(
            id = todo.id,
            createdAt = todo.createdAt,
            title = todo.title,
            content = todo.content,
            isDone = !todo.isDone
        )
        updateTodo(updatedTodo)
    }
}
