package com.example.downloadfiles

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat

const val CHANNEL_ID: String = "1"
const val NOTIFICATION_ID: Int = 2
lateinit var notificationManager: NotificationManager
lateinit var builder: NotificationCompat.Builder

fun initNotificationManager(context: Context) {
    notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
}

fun initNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "channel_name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.setSound(null, null)
        notificationManager.createNotificationChannel(channel)

    }
}

fun createNotificationChannel(context: Context, textTitle: String): Notification {
     builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.circle_notifications)
        .setContentTitle(textTitle)
        .setContentText("textContent")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setProgress(100, 0, false)
    return builder.build()
}

fun updateNotificationProgress(progress: Int) {
    builder.setProgress(100, progress, false)
        .also {
            if (progress == 100) {
                builder.setContentTitle("Download Complete")
            }
        }
}

fun notifyNotification() {
    notificationManager.notify(NOTIFICATION_ID, builder.build())
}

