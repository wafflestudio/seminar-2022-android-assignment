package com.example.simpletodo

import android.app.Application
import com.example.simpletodo.data.TodoDatabase

class SimpleTodoApplication : Application() {
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}