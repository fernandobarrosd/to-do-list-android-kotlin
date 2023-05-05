package com.fernando.todolist.classes

import android.app.Notification as NotificationAndroid
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE

class Notification(private val context: Context, private val channelID: String, private val channelName: String) {
    private var notificationManager : NotificationManager? = null
    private var notificationChannel : NotificationChannel? = null
    private var notificationBuilder: NotificationAndroid.Builder? = null

    init {
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationBuilder = NotificationAndroid.Builder(context, channelID)
    }

    fun getNotificationChannel() = notificationChannel

    fun getNotificationBuilder() = notificationBuilder

    fun setNotificationChannelInManager() {
        notificationChannel?.let { notificationManager?.createNotificationChannel(it) }
    }
    fun notify(id: Int) {
        notificationManager?.notify(id, notificationBuilder?.build())
    }


}