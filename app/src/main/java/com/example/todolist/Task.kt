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

    companion object Priority {
        val low = "LOW"
        val normal = "NORMAL"
        val high = "HIGH"
    }
}