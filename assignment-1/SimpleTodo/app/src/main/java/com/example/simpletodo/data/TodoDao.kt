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

    @Query("SELECT * from todo_table")
    fun getAll(): Flow<List<Todo>>

    @Query("SELECT * from todo_table WHERE done=0")
    fun getOngoingTodos(): Flow<List<Todo>>

    @Update
    suspend fun update(todo: Todo)
}
