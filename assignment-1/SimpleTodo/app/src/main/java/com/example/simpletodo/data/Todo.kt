package com.example.simpletodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "content")
    val content : String,
    @ColumnInfo(name = "done")
    var done : Boolean = false
)
