package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private var listItems = ArrayList<Task>()

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var toDoList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ADD HERE
        listItems.add(Task("aaaa"))
        val toDoList: ListView = findViewById(R.id.toDoList)
        val adapter = MyArrayAdapter(this, R.layout.listview_item, listItems)
        toDoList.adapter = adapter

        val button = findViewById<Button>(R.id.addTask)
        button.setOnClickListener {
            addItems(adapter)
        }
        //onCheckboxClicked()
        setupRemove(adapter, toDoList)
        setupCompleted(adapter, toDoList)
    }

    private fun addItems(adapter: MyArrayAdapter) {
        val toAdd: EditText = findViewById(R.id.taskDetails)
        adapter.add(Task(toAdd.text.toString()))
    }

    private fun setupRemove(adapter: MyArrayAdapter, toDoList: ListView){
        toDoList.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Edit or delete task?")
                .setMessage("Would you like to edit the task or delete it?")
                .setPositiveButton("Edit")
            { _, _ ->
                startActivity(Intent(this, FillTask::class.java).apply {
                    putExtra("TASK", listItems.get(position).task)
                })
            }.setNegativeButton("Delete"){
                _, _ -> adapter.remove(adapter.getItem(position))
            }
                .show()
            true
        }
    }

    private fun setupCompleted(adapter: MyArrayAdapter, toDoList: ListView){
        toDoList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            adapter.changeColor(position, view)
        }
    }

    fun onTaskClicked(view: View){
        println("dupa")
    }

    fun onCheckboxClicked(view: View){
        //val checkBox = findViewById<CheckBox>(R.id.toDoTaskCheckBox)
        //checkBox.setOnClickListener(v -> )
    }
}