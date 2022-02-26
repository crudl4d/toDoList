package com.example.todolist

import java.io.Serializable

class Task: Serializable{
    var text: String? = null
    var completed: Boolean = false
    var priority: String? = normal

    constructor(task: String){
        this.text = task
    }
    constructor()

    constructor(task: String?, completed: Boolean, priority: String?) {
        this.text = task
        this.completed = completed
        this.priority = priority
    }


    companion object Priority {
        val low = "LOW"
        val normal = "NORMAL"
        val high = "HIGH"
    }

    fun serialize(): String {
        return "${text!!.replace("&", " ")}&$completed&$priority"
    }

    override fun toString(): String{
        return "$priority | $text"
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