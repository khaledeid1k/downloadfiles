package com.example.downloadfiles.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.R

class NotificationDownload {
    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder
    companion object {
        const val CHANNEL_ID: String = "CHANNEL_ID"
        const val CHANNEL_NAME: String = "CHANNEL_NAME"
//        const val NOTIFICATION_ID: Int = 101
    }
    init {

    }

    fun initNotificationManager(context: Context) {
        notificationManager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotificationChannel(
        context: Context,
        textTitle: String = "Content Title"
    ): Notification {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle(textTitle)
            .setContentText("downloading...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, 0, false)
        return builder.build()
    }
}