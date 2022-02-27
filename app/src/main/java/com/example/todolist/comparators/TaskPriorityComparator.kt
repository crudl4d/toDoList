package com.example.todolist.comparators

import com.example.todolist.Task
import com.example.todolist.TaskData
import java.util.*

class TaskPriorityComparator: Comparator<TaskData>{
    override fun compare(o1: TaskData?, o2: TaskData?): Int {
        return if(o2!!.priority == o1!!.priority)
            0
        else if(o1.priority == Task.low && o2.priority != Task.low)
            1
        else if(o1.priority.equals(Task.normal) && o2.priority.equals(Task.high))
            1
        else
            -1
    }
}