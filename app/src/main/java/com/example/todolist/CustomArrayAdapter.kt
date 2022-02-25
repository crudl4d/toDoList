package com.example.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class MyArrayAdapter(
    context: Context?,
    resourceId: Int,
    private val items: List<Task?>?
) : ArrayAdapter<Task?>(context!!, resourceId, items!!){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false)
        }
        val toDoTask = view!!.findViewById<TextView>(R.id.toDoTask)
        toDoTask.text = items!![position]!!.task
        toDoTask.setBackgroundColor(if(items[position]!!.completed) Color.GREEN else Color.RED)
        return view
    }

    fun changeColor(position: Int, view: View?){
        val toDoTask = view!!.findViewById<TextView>(R.id.toDoTask)
        toDoTask.text = items!![position]!!.task
        items[position]!!.completed = !items[position]!!.completed
        toDoTask.setBackgroundColor(if(items[position]!!.completed) Color.GREEN else Color.RED)
    }
}