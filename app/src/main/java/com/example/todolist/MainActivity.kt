package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.todolist.comparators.TaskPriorityComparator


class MainActivity : AppCompatActivity() {

    private var listItems = ArrayList<TaskData>()
    private lateinit var adapter: MyArrayAdapter
    private lateinit var toDoList: ListView
    private val fileUtil: FileUtil = FileUtil()
    private lateinit var db: AppDatabase
    private lateinit var taskDao: TaskDao

    companion object{
        private val EDIT_TASK = 0
        private val ADD_TASK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "toDoDb"
            ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        taskDao = db.taskDao()
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
            }, ADD_TASK)
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
                }, EDIT_TASK)
            }.setNegativeButton("Delete"){ _, _ ->
                    val toDelete = adapter.getItem(position)!!
                    taskDao.delete(toDelete)
                    adapter.remove(toDelete)
            }
                .show()
            true
        }
    }

    private fun setupCompleted(){
        toDoList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            taskDao.changeCompleted(position, adapter.changeCompleted(position, view))
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
        if(resultCode == RESULT_CANCELED) {
            return
        }
        var task = data!!.getSerializableExtra("TASK") as TaskData
        task = TaskData(null, task.text, task.completed!!, task.priority, task.pictures)
        if(requestCode == ADD_TASK){
            taskDao.insert(task)
            adapter.add(task)
            return
        }
        val editedId = data.getIntExtra("TASK_ID", -1)
        task.id = editedId
        taskDao.update(task)
        listItems[editedId] = data.getSerializableExtra("TASK") as TaskData
        adapter.notifyDataSetChanged()
    }

    private fun populateTasks(){
//        val tasksFromFile = fileUtil.readFromFile(this)!!.trim()
//        val tasksSplit = tasksFromFile!!.split("\n")
//        for(task in tasksSplit){
//            try {
//                listItems.add(deserialize(task))
//            } catch (e: IndexOutOfBoundsException){
//                break
//            }
//        }
//        taskDao.deleteAll()
//        taskDao.insert(TaskData("Clean room", true, "NORMAL", ""))
//        taskDao.insert(TaskData("Buy groceries", true, "NORMAL", ""))
//        taskDao.insert(TaskData("Do homework", true, "NORMAL", ""))
//        taskDao.insert(TaskData("Walk the dog", true, "NORMAL", ""))
//        listItems.add(TaskData("Clean room", true, "NORMAL", ""))
//        listItems.add(TaskData("Buy groceries", true, "NORMAL", ""))
//        listItems.add(TaskData("Do homework", true, "NORMAL", ""))
//        listItems.add(TaskData("Walk the dog", true, "NORMAL", ""))
        listItems.addAll(taskDao.getAll())
    }
}