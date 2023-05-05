package com.fernando.todolist.classes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fernando.todolist.R
import com.fernando.todolist.screens.TaskInfoActivity

class TaskAdapter(private val tasks : ArrayList<HashMap<String, Any>>) : Adapter<TaskAdapter.TaskHolder>() {
    class TaskHolder(itemView : View) :
        ViewHolder(itemView) {
        var taskNameText : TextView = itemView.findViewById(R.id.taskNameText)
        var infoTaskButton : ImageButton = itemView.findViewById(R.id.infoTaskButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TaskHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_task, parent, false)
        return TaskHolder(view)
    }


    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasks[position]
        val id : String = task["id"] as String
        val name = task["name"].toString()

        holder.taskNameText.text = name
        holder.infoTaskButton.setOnClickListener {
            val taskInfo = Intent(holder.itemView.context, TaskInfoActivity::class.java)
            taskInfo.putExtra("id", id)
            holder.itemView.context.startActivity(taskInfo)
        }

    }

    override fun getItemCount() = tasks.size
}
