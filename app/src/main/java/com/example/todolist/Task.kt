package com.example.todolist

import java.io.Serializable

class Task: Serializable{
    var task: String? = null
    var completed: Boolean = false
    var priority: String? = normal

    constructor(task: String){
        this.task = task
    }
    constructor()

    constructor(task: String?, completed: Boolean, priority: String?) {
        this.task = task
        this.completed = completed
        this.priority = priority
    }


    companion object Priority {
        val low = "LOW"
        val normal = "NORMAL"
        val high = "HIGH"
    }

    override fun toString(): String {
        return "$task&$completed&$priority"
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