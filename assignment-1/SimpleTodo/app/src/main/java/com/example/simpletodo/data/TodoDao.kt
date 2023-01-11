package com.example.simpletodo.data

import androidx.room.*
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
    suspend fun undoneToDone(id: Long)
    @Query("UPDATE todo_table SET done = 0 WHERE id = :id")
    suspend fun doneToUndone(id: Long)
    @Query("SELECT * from todo_table WHERE done = 0 ORDER BY id DESC")
    fun getUndoneList(): Flow<List<Todo>>
}
