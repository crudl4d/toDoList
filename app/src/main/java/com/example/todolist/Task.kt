package com.example.todolist

class Task {
    var task: String? = null
    var completed: Boolean = false

    constructor(task: String){
        this.task = task
    }
    constructor()
}