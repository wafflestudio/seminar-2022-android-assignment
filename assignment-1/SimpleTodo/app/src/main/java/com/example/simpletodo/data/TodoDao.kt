package com.example.simpletodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * from todo_table WHERE id = :id")
    fun getTodo(id: Long): Flow<Todo>
    @Query("SELECT * from todo_table ORDER BY created_at ASC")
    fun getTodos(): Flow<List<Todo>>
    @Query("SELECT * from todo_table WHERE is_done = 'false'")
    fun getUndoneTodos(): Flow<List<Todo>>
}
