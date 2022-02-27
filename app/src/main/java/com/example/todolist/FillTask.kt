package com.example.todolist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.room.Room

class FillTask : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_task2)

        ///////////////////
        val task = intent.getStringExtra("TASK_TEXT")
        setPriority(intent.getStringExtra("TASK_PRIORITY"))
        findViewById<TextView>(R.id.taskSecondActivity).text = task
        findViewById<Button>(R.id.fillSaveButton).setOnClickListener { _ ->
            val returnIntent = Intent()
            returnIntent.putExtra("TASK", fillTask())
            returnIntent.putExtra("TASK_ID", intent.getIntExtra("TASK_ID", -1))
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun fillTask(): TaskData{
        val task = TaskData( "", true, null, null)
        task.text = findViewById<TextView>(R.id.taskSecondActivity).text.toString()
        when(findViewById<RadioGroup>(R.id.priority).checkedRadioButtonId){
            R.id.lowPriority -> task.priority = Task.low
            R.id.normalPriority -> task.priority = Task.normal
            R.id.highPriority -> task.priority = Task.high
        }
        return task
    }

    private fun setPriority(priority: String?){
        findViewById<RadioGroup>(R.id.priority).check(when(priority){
            TaskData.low -> R.id.lowPriority
            TaskData.normal -> R.id.normalPriority
            else -> R.id.highPriority
        })
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitmap)
        }
    }
}
