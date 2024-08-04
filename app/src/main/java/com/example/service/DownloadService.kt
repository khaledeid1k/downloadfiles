package com.example.service

import android.app.Service
import android.content.Intent
import android.util.Log
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.network.FileDownloader
import com.example.downloadfiles.ui.NotificationDownload
import com.example.downloadfiles.ui.NotificationDownload.Companion.NOTIFICATION_ID
import kotlinx.coroutines.flow.update

class DownloadService : Service() {

   private var baseViewModel: BaseViewModel = SharedDataHolder.baseViewModel
    private val notificationDownload =  NotificationDownload()



    override fun onCreate() {
        super.onCreate()
       notificationDownload.initNotificationManager(this)
        notificationDownload.initNotificationChannel()
        startForeground(NOTIFICATION_ID,notificationDownload.createNotificationChannel(this))
        startDownload()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


    private fun startDownload() {
        FileDownloader().downloadFile("555555cccccccccccccccccc.mp3") { progress ->
            Log.d("dddddddddddddddddddd", "startDownload:$progress ")
            Log.d("dddddddddddddddddddd", "SharedDataHolder:${ SharedDataHolder.baseViewModel} ")

            baseViewModel.updateNotificationProcess.update {
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

    override fun onBind(intent: Intent?) = null





}
