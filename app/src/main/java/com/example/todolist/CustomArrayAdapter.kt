package com.example.todolist

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import androidx.appcompat.widget.DrawableUtils

class MyArrayAdapter(
    context: Context?,
    resourceId: Int,
    private val items: List<Task?>?
) : ArrayAdapter<Task?>(context!!, resourceId, items!!){

    val fileUtil: FileUtil = FileUtil()

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view: View? = view
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        checkUncheck(view!!.findViewById<CheckedTextView>(R.id.toDoTask), position)
        return view
    }

    fun changeCompleted(position: Int, view: View?) {
        items?.get(position)!!.completed = !items[position]!!.completed
        checkUncheck(view!!.findViewById<CheckedTextView>(R.id.toDoTask), position)
    }

    private fun checkUncheck(toDoTask: CheckedTextView, position: Int){
        toDoTask.text = items!![position]!!.toString()
        if(items?.get(position)!!.completed){
            toDoTask.setBackgroundColor(Color.GREEN)
            toDoTask.isChecked = true
            toDoTask.setCheckMarkDrawable(android.R.drawable.checkbox_on_background)
        }
        else {
            toDoTask.setBackgroundColor(Color.RED)
            toDoTask.isChecked = false
            toDoTask.setCheckMarkDrawable(android.R.drawable.checkbox_off_background)
        }
    }

    fun saveToFile(){
        var tasks = ""
        for(i in 0 until super.getCount()){
            tasks = tasks.plus(getItem(i)!!.serialize() + "\n")
        }
        fileUtil.writeToFile(tasks, context)
    }

}