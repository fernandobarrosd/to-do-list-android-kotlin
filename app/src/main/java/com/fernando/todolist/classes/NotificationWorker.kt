package com.fernando.todolist.classes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fernando.todolist.R

class NotificationWorker(context : Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {
    private var notificationManager : NotificationManager? = null




    override fun doWork(): Result {
        val notification = Notification(applicationContext, "Channel 1", "App rodando")
        notification.getNotificationChannel()?.description = "${applicationContext.getString(R.string.app_name)}: notificação quando o app é executado."
        notification.setNotificationChannelInManager()

        notification.getNotificationBuilder()
            ?.setSmallIcon(R.mipmap.ic_launcher)
            ?.setContentText("App em em execução")
            ?.setContentTitle(applicationContext.getString(R.string.app_name))

        notification.notify(1)
        return Result.success()
    }




}