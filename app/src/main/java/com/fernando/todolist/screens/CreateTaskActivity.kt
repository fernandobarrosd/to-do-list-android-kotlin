package com.fernando.todolist.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.fernando.todolist.R
import com.fernando.todolist.classes.CustomActivity
import com.fernando.todolist.components.CustomSnackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class CreateTaskActivity : CustomActivity() {
    private var btnAddTask : Button? = null
    private var btnBackToHomeScreen: ImageButton? = null
    private var taskNameInput : EditText? = null
    private var taskDescriptionInput : EditText? = null
    private var alertDialogView : View? = null
    private var alertDialog : AlertDialog? = null

    private var taskName = ""
    private var taskDescription = ""
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activty_create_task)
        this.init()
    }

    override fun onResume() {
        super.onResume()
        initAlertDialogView()
        initAlertDialog()
        initEvents()
    }

    private fun init() {
        btnAddTask = findViewById(R.id.btnAddTask)
        btnBackToHomeScreen = findViewById(R.id.btnBackToHomeScreen)
        taskNameInput = findViewById(R.id.taskNameInput)
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput)
    }


    @SuppressLint("ResourceAsColor")
    private fun initEvents() {
        btnAddTask?.setOnClickListener {
            taskName = taskNameInput?.text.toString().trim()
            taskDescription = taskDescriptionInput?.text.toString().trim()

            when {
                taskName.isEmpty() || taskDescription.isEmpty() -> {
                    val snackbar = CustomSnackbar().Build()
                        .setView(this)
                        .setText("Task fields is invalid")
                        .setIcon(R.drawable.ic_error)
                        .setBackgroundColor(android.R.color.holo_red_dark)
                        .build(it)

                    snackbar.show()

                }
                else -> alertDialog?.show()
            }
        }
        btnBackToHomeScreen?.setOnClickListener{
            navigateTo(MainActivity::class.java)
        }
    }

    @SuppressLint("InflateParams")
    private fun initAlertDialogView() {
        alertDialogView = layoutInflater.
        inflate(R.layout.layout_create_task_alert_dialog, null)

        val btnCancelAddTask = alertDialogView?.findViewById<Button>(R.id.btnCancelAddTask)

        val btnConfirmAddTask = alertDialogView?.findViewById<Button>(R.id.btnConfirmAddTask)


        btnConfirmAddTask?.setOnClickListener {
            alertDialog?.dismiss()
            window.currentFocus?.rootView?.let { it1 -> addTask(it1) }
        }

        btnCancelAddTask?.setOnClickListener{
            alertDialog?.dismiss()
        }

    }

    private fun initAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setView(alertDialogView)
        alertDialog = alertDialogBuilder.create()
    }

    private fun addTask(view: View) {
        val taskData = hashMapOf<String, Any>()
        taskData["name"] = taskName
        taskData["description"] = taskDescription
        taskData["finished"] = false
        saveTask(view, taskData)
    }

    private fun saveTask(view: View, task : HashMap<String, Any>) {
        db.collection("task")
               .document()
               .set(task).addOnSuccessListener{
                Snackbar.make(view,
                    "Task is created!", Snackbar.LENGTH_LONG).show()
            }
    }
}