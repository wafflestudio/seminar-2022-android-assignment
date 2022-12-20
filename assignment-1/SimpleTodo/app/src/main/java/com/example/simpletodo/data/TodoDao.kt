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

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE (CASE WHEN :showDone = 1 Then is_done IS NOT NULL ELSE is_done = 0 End)")
    fun getUserTodo(showDone: Boolean): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): Flow<List<Todo>>

    // get not done Todo_Raws
    @Query("SELECT * FROM todo_table WHERE is_done = 0")
    fun getPerceivedTodo(): Flow<List<Todo>>
}
