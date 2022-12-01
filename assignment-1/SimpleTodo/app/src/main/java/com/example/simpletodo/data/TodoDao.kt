package com.example.simpletodo.data

import android.content.ClipData
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
    @Delete
    suspend fun deleteTodo(todo: Todo)
    @Query("SELECT * from todo_table WHERE id = :id")
    fun getTodo(id: Long): Flow<Todo>
    @Query("SELECT * from todo_table ORDER BY id DESC")
    fun getTodoList(): Flow<List<Todo>>
    @Query("UPDATE todo_table SET done = 1 WHERE id = :id")
    fun undoneToDone(id: Long)
    @Query("UPDATE todo_table SET done = 0 WHERE id = :id")
    fun doneToUndone(id: Long)
    @Query("SELECT * from todo_table WHERE done = 1 ORDER BY id DESC")
    fun getDoneList(): Flow<List<Todo>>
}
