package com.example.service

import android.app.Service
import android.content.Intent
import com.example.downloadfiles.createNotificationChannel
import com.example.downloadfiles.network.DownloadFileR

class DownloadService : Service() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this, "sdssadsdasdddddasada")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        DownloadFileR().downloadFile{ stopService(intent) }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?) = null


    override fun onDestroy() {
        super.onDestroy()
    }

}