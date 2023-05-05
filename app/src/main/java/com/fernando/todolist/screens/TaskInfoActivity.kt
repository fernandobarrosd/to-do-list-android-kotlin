package com.fernando.todolist.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.fernando.todolist.R
import com.fernando.todolist.classes.CustomActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.HashMap

class TaskInfoActivity : CustomActivity() {
    private var btnDeleteTask : Button? = null
    private var btnUpdateTask : Button? = null
    private var taskNameInput : EditText? = null
    private var taskDescriptionInput : EditText? = null
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var finishSwitch : Switch? = null
    private var id : String? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.layout_task_info)
        init()
        initEvents()
        initTask()
    }

    private fun init() {
        btnDeleteTask = findViewById(R.id.btnDeleteTask)
        btnUpdateTask = findViewById(R.id.btnUpdateTask)
        taskNameInput = findViewById(R.id.taskNameInput)
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput)
        finishSwitch = findViewById(R.id.finishSwitch)
        id = intent.getStringExtra("id")
    }

    private fun initEvents() {
        btnDeleteTask?.setOnClickListener {
            deleteTask()
            val data = arrayListOf<Boolean>()
            data.add(true)
            navigateTo(MainActivity::class.java, "data", data)
        }
        btnUpdateTask?.setOnClickListener {
            val taskData = hashMapOf<String, Any>()
            taskData["name"] = taskNameInput?.text.toString().trim()
            taskData["description"] = taskDescriptionInput?.text.toString().trim()
            taskData["finished"] = finishSwitch?.isChecked as Boolean
            updateTask(window.decorView, taskData)
        }
    }

    private fun initTask() {
        db.collection("task")
                .document(id.toString())
                .get()
                .addOnSuccessListener {
                    val task = hashMapOf<String, Any>()

                    task["id"] = it.id
                    task["name"] =
                        it.getString("name") as String
                    task["description"] =
                        it.getString("description") as String
                    task["finished"] =
                        it.getBoolean("finished") as Boolean

                    taskNameInput?.setText(task["name"].toString())
                    taskDescriptionInput?.setText(task["description"].toString())
                    finishSwitch?.isChecked = task["finished"].toString().toBoolean()

                }
    }

    private fun deleteTask() {
        db.collection("task").document(id.toString())
                .delete()
        val hashMap = hashMapOf<String, Boolean>()






    }

    private fun updateTask(view: View, task : HashMap<String, Any>) {
        val snackbar : Snackbar
        when {
            task["name"].toString().isEmpty() || task["description"].toString().isEmpty() ->
                snackbar = Snackbar.make(view,
                "Task fields is invalid", Snackbar.LENGTH_LONG)

            else -> {
                db.collection("task").document(id.toString())
                    .update(task)
                snackbar = Snackbar.make(view, "Task is updated!", Snackbar.LENGTH_LONG)
            }
        }
        snackbar.show()

    }
}