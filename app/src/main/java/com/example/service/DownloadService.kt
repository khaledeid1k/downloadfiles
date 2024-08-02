package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.R
import com.example.downloadfiles.network.FileDownloader

class DownloadService : Service() {
    companion object{
        const val CHANNEL_ID: String = "CHANNEL_ID"
        const val CHANNEL_NAME: String = "CHANNEL_NAME"
        const val NOTIFICATION_ID: Int = 101
    }


    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate() {
        super.onCreate()
        initNotificationManager(this)
        initNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotificationChannel(this))
        startDownload()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


    private fun startDownload() {
        FileDownloader().downloadFile ("lol1.mp3"){ progress ->
            builder.setProgress(100, progress, false)
            builder.setContentTitle("$progress %")
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    override fun onBind(intent: Intent?) = null



    private fun initNotificationManager(context: Context) {
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun createNotificationChannel(context: Context, textTitle: String = "Content Title"): Notification {
        builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle(textTitle)
            .setContentText("0 %")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, 0, false)
        return builder.build()
    }
}
