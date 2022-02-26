package com.example.todolist.comparators

import com.example.todolist.Task
import java.util.Comparator

class TaskPriorityComparator: Comparator<Task>{
    override fun compare(o1: Task?, o2: Task?): Int {
        return if(o1!!.priority == Task.low && o2!!.priority != Task.low)
            1
        else if(o1.priority.equals(Task.normal) && o2!!.priority.equals(Task.high))
            1
        else
            -1
    }
}