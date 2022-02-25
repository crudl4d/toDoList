package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FillTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_task2)
        ///////////////////
        val task = intent.getStringExtra("TASK")
        findViewById<TextView>(R.id.taskSecondActivity).text = task
    }
}