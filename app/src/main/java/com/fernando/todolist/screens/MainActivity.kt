package com.fernando.todolist.screens

import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.fernando.todolist.R
import com.fernando.todolist.classes.CustomActivity
import com.fernando.todolist.classes.NotificationWorker
import com.fernando.todolist.classes.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Boolean

class MainActivity : CustomActivity() {
    private var btnGoToTaskScreen : FloatingActionButton? = null
    private var taskList : RecyclerView? = null
    private var loading : ProgressBar? = null
    private val db = FirebaseFirestore.getInstance()
    private var notificationWorkRequest : WorkRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        this.initRecyclerView()


    }

    override fun onResume() {
        super.onResume()
        initEvents()
        this.initAdapter()

    }

    override fun onStart() {
        super.onStart()
        if (notificationWorkRequest == null) {
            return
        }
        else {
            notificationWorkRequest?.id?.let { WorkManager.getInstance(this).cancelWorkById(it) }
        }

    }

    override fun onPause() {
        super.onPause()
        notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        notificationWorkRequest?.let { WorkManager.getInstance(this).enqueue(it) }

    }


    private fun init() {
        btnGoToTaskScreen = findViewById(R.id.btnGoToTaskScreen)
        taskList = findViewById(R.id.tasksList)
        loading = findViewById(R.id.loading)
        val isDeleted = intent.getBooleanArrayExtra("data")?.get(0)

        if (Boolean.parseBoolean(isDeleted.toString())) {
            Snackbar.make(window.decorView,
                "Task is deleted", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initEvents() {
        btnGoToTaskScreen?.setOnClickListener {
            navigateTo(CreateTaskActivity::class.java)
        }

    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        taskList?.layoutManager = linearLayoutManager
    }

    private fun initAdapter() {
        db.collection("task")
            .addSnapshotListener { querySnapshot, _ ->
                    val taskData : ArrayList<HashMap<String, Any>> = arrayListOf()
                    querySnapshot?.forEach {
                   val task = hashMapOf<String, Any>()
                        task["id"] = it.id
                        task["name"] = it.getString("name").toString()
                        task["description"] = it.getString("description").toString()
                        taskData.add(task)
                }
                    taskList?.adapter = TaskAdapter(taskData)
                    removeLoading()

                }
    }


    private fun removeLoading() {
        loading?.isIndeterminate = false
        loading?.visibility = View.INVISIBLE
    }
}