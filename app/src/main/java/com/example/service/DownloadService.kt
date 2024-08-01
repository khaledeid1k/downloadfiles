package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.CHANNEL_ID
import com.example.downloadfiles.R
import com.example.downloadfiles.network.DownloadFileR

class DownloadService : Service() {

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder
    lateinit var handeler: Handler
    var progress = 0

    companion object {
        const val NOTIFICATION_ID: Int = 2


    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        handeler = Handler()
        initNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        updatePreogress()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun createNotification(): Notification {
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setContentTitle("dsfdsfds")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, progress, false)
            .setSilent(true)

        return builder.build()
    }

    private fun updateNotification() {
        builder.setContentText("Dpwnloading").setProgress(100, progress, false)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun updatePreogress() {
        DownloadFileR().downloadFile(
            progressTrack = {
                progress=it

            }
        )
    handeler.postDelayed( {
        if (progress <= 100) {
            updateNotification()
            updatePreogress()
        } else {
            stopForeground(true)
            stopSelf()
        }
    },1000)

//        DownloadFileR().downloadFile(
//            progressTrack = {
//                Log.d("afsdfsdfsd", "updatePreogress: $it")
//                progress=it
//                if (progress <= 100) {
//                    updateNotification()
//                    updatePreogress()
//                } else {
//                    stopForeground(true)
//                    stopSelf()
//                }
//            }
//        )

    }

    override fun onBind(intent: Intent?) = null


}
