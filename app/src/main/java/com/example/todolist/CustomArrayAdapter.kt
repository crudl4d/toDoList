package com.example.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView

class MyArrayAdapter(
    context: Context?,
    resourceId: Int,
    private val items: List<Task?>?
) : ArrayAdapter<Task?>(context!!, resourceId, items!!){

    val fileUtil: FileUtil = FileUtil()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val toDoTask = view!!.findViewById<CheckedTextView>(R.id.toDoTask)
        toDoTask.text = items!![position]!!.toString()
        toDoTask.setBackgroundColor(if(items[position]!!.completed) Color.GREEN else Color.RED)
        return view
    }

    fun changeColor(position: Int, view: View?){
        val toDoTask = view!!.findViewById<CheckedTextView>(R.id.toDoTask)
        toDoTask.text = items!![position]!!.text
        items[position]!!.completed = !items[position]!!.completed
        toDoTask.setBackgroundColor(if(items[position]!!.completed) Color.GREEN else Color.RED)
    }

    fun saveToFile(){
        var tasks = ""
        for(i in 0 until super.getCount()){
            tasks = tasks.plus(getItem(i)!!.serialize() + "\n")
        }
        fileUtil.writeToFile(tasks, context)
    }

}