package com.example.todolist

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskData")
    fun getAll(): List<TaskData>

    @Query("SELECT * FROM taskData WHERE id=(:id)")
    fun get(id: Int): TaskData

    @Query("SELECT * FROM taskData WHERE id IN (:taskIds)")
    fun loadAllByIds(taskIds: IntArray): List<TaskData>

    @Insert
    fun insert(vararg  task: TaskData)

    @Insert
    fun insertAll(tasks: Array<TaskData>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: TaskData)

    @Delete
    fun delete(user: TaskData)

    @Query("UPDATE taskdata SET completed=(:completed) WHERE id=(:id)")
    fun changeCompleted(id:Int, completed: Boolean)

    @Query("DELETE FROM taskData WHERE 1")
    fun deleteAll()

    companion object Priority {
        val low = "LOW"
        val normal = "NORMAL"
        val high = "HIGH"
    }
}