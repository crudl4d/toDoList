package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.comparators.TaskPriorityComparator


class MainActivity : AppCompatActivity() {

    private var listItems = ArrayList<Task>()
    private lateinit var adapter: MyArrayAdapter
    private lateinit var toDoList: ListView
    private val fileUtil: FileUtil = FileUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ADD HERE
        populateTasks()
        toDoList = findViewById(R.id.toDoList)
        adapter = MyArrayAdapter(this, R.layout.list_item, listItems)
        toDoList.adapter = adapter

        setupAddItems()
        setupRemove()
        setupCompleted()
        setupSort()
    }

    override fun onDestroy() {
        adapter.saveToFile()
        super.onDestroy()
    }

    private fun setupAddItems() {
        val button = findViewById<Button>(R.id.addTask)
        button.setOnClickListener {
            startActivityForResult(Intent(this, FillTask::class.java).apply {
            }, 1)
        }
    }

    private fun setupRemove(){
        toDoList.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Edit or delete task?")
                .setMessage("Would you like to edit the task or delete it?")
                .setPositiveButton("Edit")
            { _, _ ->
                startActivityForResult(Intent(this, FillTask::class.java).apply {
                    putExtra("TASK_TEXT", listItems.get(position).text)
                    putExtra("TASK_ID", position)
                    putExtra("TASK_PRIORITY", listItems.get(position).priority)
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
            adapter.changeCompleted(position, view)
        }
    }

    private fun setupSort(){
        findViewById<Spinner>(R.id.sort).onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(findViewById<Spinner>(R.id.sort).selectedItem){
                    "Alphabetically" -> listItems.sortBy { task -> task.text }
                    "Priority" -> listItems.sortWith(TaskPriorityComparator())
                    "Completed" -> listItems.sortBy { task -> task.completed }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CANCELED)
            return
        if(requestCode == 1){
            adapter.insert(data!!.getSerializableExtra("TASK") as Task, 0)
            return
        }
        listItems[data!!.getIntExtra("TASK_ID", -1)] = data.getSerializableExtra("TASK") as Task
        adapter.notifyDataSetChanged()
    }

    private fun populateTasks(){
//        listItems.add(Task("Clean room"))
//        listItems.add(Task("Buy groceries"))
//        listItems.add(Task("Do homework"))
//        listItems.add(Task("Walk the dog"))
        val tasksFromFile = fileUtil.readFromFile(this)!!.trim()
        val tasksSplit = tasksFromFile!!.split("\n")
        for(task in tasksSplit){
            try {
                listItems.add(deserialize(task))
            } catch (e: IndexOutOfBoundsException){
                break
            }
        }
    }
}