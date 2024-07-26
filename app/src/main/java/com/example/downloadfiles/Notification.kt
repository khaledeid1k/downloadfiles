package com.example.downloadfiles

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
        notificationManager.createNotificationChannel(channel)

    }
}

fun createNotificationChannel(context: Context, textTitle: String) {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle(textTitle)
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, 0, false)

    notificationManager.notify(NOTIFICATION_ID, builder.build())
}

fun updateNotificationProgress(context: Context, progress: Int) {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle("Downloading")
            .setContentText("Downloading file...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, progress, false)
            .setShowWhen(true)
    notificationManager.notify(NOTIFICATION_ID, builder.build())
}

fun completeNotification(context: Context) {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle("Download Complete")
            .setContentText("The file has been downloaded successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(0, 0, false)
            .setShowWhen(true)

    notificationManager.notify(NOTIFICATION_ID, builder.build())
}

