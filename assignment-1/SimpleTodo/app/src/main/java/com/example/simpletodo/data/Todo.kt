package com.example.simpletodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    @NotNull
    val title: String,

    @ColumnInfo(name = "content")
    val content: String?,

    @ColumnInfo(name = "is_done")
    @NotNull
    val isDone: Boolean

    /*
    @ColumnInfo(name = "deadline")
    @NotNull
    val deadline: String,
     */
)
