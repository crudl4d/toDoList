package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get


class MainActivity : AppCompatActivity() {

    private var listItems = ArrayList<Task>()
    private lateinit var adapter: MyArrayAdapter
    private lateinit var toDoList: ListView
    private val fileUtil: FileUtil = FileUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ADD HERE
        populate()
        toDoList = findViewById(R.id.toDoList)
        adapter = MyArrayAdapter(this, R.layout.listview_item, listItems)
        toDoList.adapter = adapter

        val button = findViewById<Button>(R.id.addTask)
        button.setOnClickListener {
            addItems()
        }
        setupRemove()
        setupCompleted()
    }

    private fun addItems() {
        val toAdd: String = findViewById<EditText>(R.id.taskDetails).text.toString()
        adapter.add(Task(toAdd))
    }

    private fun setupRemove(){
        toDoList.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Edit or delete task?")
                .setMessage("Would you like to edit the task or delete it?")
                .setPositiveButton("Edit")
            { _, _ ->
                startActivityForResult(Intent(this, FillTask::class.java).apply {
                    putExtra("TASK", listItems.get(position).task)
                    putExtra("TASK_ID", position)
                }, 0)
            }.setNegativeButton("Delete"){
                _, _ -> adapter.remove(adapter.getItem(position))
            }
                .show()
            true
        }
    }

    private fun setupCompleted(){
        toDoList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            adapter.changeColor(position, view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        listItems[data!!.getIntExtra("TASK_ID", -1)] = data.getSerializableExtra("TASK") as Task
        adapter.notifyDataSetChanged()
    }

    private fun populate(){
        listItems.add(Task("Clean room"))
        listItems.add(Task("Buy groceries"))
        listItems.add(Task("Do homework"))
        listItems.add(Task("Walk the dog"))
    }
}