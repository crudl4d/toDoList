package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class FillTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_task2)
        ///////////////////
        val task = intent.getStringExtra("TASK")
        findViewById<TextView>(R.id.taskSecondActivity).text = task
        findViewById<Button>(R.id.fillSaveButton).setOnClickListener { _ ->
            val returnIntent = Intent()
            returnIntent.putExtra("TASK", fillTask())
            returnIntent.putExtra("TASK_ID", intent.getIntExtra("TASK_ID", -1))
            setResult(RESULT_OK, returnIntent)
            finishActivity(0)
            finish()
        }
    }

    private fun fillTask(): Task{
        val task = Task()
        task.task = findViewById<TextView>(R.id.taskSecondActivity).text.toString()
        when(findViewById<RadioGroup>(R.id.priority).checkedRadioButtonId){
            R.id.lowPriority -> task.priority = Task.low
            R.id.normalPriority -> task.priority = Task.normal
            R.id.highPriority -> task.priority = Task.high
        }
        return task
    }
}