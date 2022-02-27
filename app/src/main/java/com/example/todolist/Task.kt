package com.example.todolist

import android.graphics.Bitmap
import java.io.Serializable

class Task: Serializable{
    var text: String? = null
    var completed: Boolean = false
    var priority: String? = normal
    var pictures: Array<Bitmap>? = null

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
