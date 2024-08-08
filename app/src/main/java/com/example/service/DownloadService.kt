package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.network.FileDownloader
import com.example.downloadfiles.ui.NotificationDownload
import com.example.downloadfiles.ui.NotificationDownload.Companion.NOTIFICATION_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DownloadService : Service() {

    val updateNotificationProcess = MutableStateFlow(0)
    private val notificationDownload =  NotificationDownload()
    private val binder = LocalBinder()



    override fun onCreate() {
        super.onCreate()
       notificationDownload.initNotificationManager(this)
        notificationDownload.initNotificationChannel()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startDownload()
        return START_STICKY
    }
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): DownloadService = this@DownloadService
    }


    private fun startDownload() {
        startForeground(NOTIFICATION_ID,notificationDownload.createNotificationChannel(this))
        FileDownloader().downloadFile("asdsadsaww.mp3") { progress ->
            Log.d("dddddddddddddddddddd", "startDownload:$progress ")
            Log.d("dddddddddddddddddddd", "SharedDataHolder:${ SharedDataHolder.baseViewModel} ")

            updateNotificationProcess.update {
                progress
            }

            notificationDownload.builder.setProgress(100, progress, false)
            notificationDownload.builder.setContentTitle("$progress %")
            notificationDownload.notificationManager.notify(NOTIFICATION_ID,notificationDownload.builder.build())
            if (progress == 100) {
                    stopSelf()

            }
        }

    }

    override fun onBind(intent: Intent?) = binder





}
