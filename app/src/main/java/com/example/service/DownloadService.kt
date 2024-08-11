package com.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.network.FileDownloader
import com.example.downloadfiles.ui.NotificationDownload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log

class DownloadService : Service() {

    val stateFlows: MutableMap<Int, MutableStateFlow<Int>> =
        mutableMapOf(Pair(0, MutableStateFlow(0)))
    private val notificationDownload = NotificationDownload()
    private val binder = LocalBinder()
    val updateNotificationProcess = MutableStateFlow(0)


    override fun onCreate() {
        super.onCreate()
        notificationDownload.initNotificationManager(this)
        notificationDownload.initNotificationChannel()

    }


    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): DownloadService = this@DownloadService
    }

    private var counter = 0

    private val _queue = MutableStateFlow(mutableMapOf<Int, Int>())
    val queue = _queue.asStateFlow()

    fun startDownload(fileName: String, url: String, nationalId: Int) {
        if (!queue.value.containsKey(nationalId)) {
            startForeground(nationalId, notificationDownload.createNotificationChannel(this))
            FileDownloader().downloadFile(url, fileName) { progress ->
                _queue.update {
                    val temp = mutableMapOf<Int, Int>()
                    temp.putAll(queue.value)
                    temp[nationalId] = progress
                    temp
                }
                notificationDownload.builder.setProgress(100, queue.value[nationalId]?:0, false)
                notificationDownload.builder.setContentTitle("${queue.value[nationalId]?:0} %")
                notificationDownload.notificationManager.notify(
                    nationalId,
                    notificationDownload.builder.build()
                )
                if (progress == 100) {
                    stopSelf()

                }
            }
        }
    }

    override fun onBind(intent: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_STICKY


}
