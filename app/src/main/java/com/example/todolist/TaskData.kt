package com.example.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskData(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "text") var text: String?,
    @ColumnInfo(name = "completed") var completed: Boolean?,
    @ColumnInfo(name = "priority") var priority: String?,
    @ColumnInfo(name = "pictures") var pictures: String?
): Serializable{
    constructor(text: String?, completed: Boolean, priority: String?, pictures: String?) : this(0, text, completed, priority, pictures)

    fun serialize(): String {
        return "${text!!.replace("&", " ")}&$completed&$priority"
    }

    override fun toString(): String{
        return "$priority | $text"
    }

    companion object Priority {
        val low = "LOW"
        val normal = "NORMAL"
        val high = "HIGH"
    }
}
fun deserialize(task: String): Task{
    val splitTask = task.split("&")
    return Task(
        splitTask[0],
        splitTask[1].toBoolean(),
        splitTask[2]
    )
}