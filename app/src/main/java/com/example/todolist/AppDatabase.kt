package com.example.todolist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskData::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}