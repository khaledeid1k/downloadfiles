package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.core.app.NotificationCompat
import com.example.downloadfiles.R
import com.example.downloadfiles.network.FileDownloader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DownloadService : Service() {
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val binder: LocalBinder by lazy {
        LocalBinder()
    }
    private val builders = mutableMapOf<Int, NotificationCompat.Builder>()
    private val _queueState = MutableStateFlow(mutableStateMapOf<Int, Int>())
    val queueState = _queueState.asStateFlow()



    override fun onCreate() {
        super.onCreate()
        initNotificationChannel()
    }


    private fun createNotificationBuilder(context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.circle_notifications)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setProgress(100, 0, false)
    }





    fun startDownload(fileName: String, url: String, nationalId: Int) {
        if (!queueState.value.containsKey(nationalId)) {
            builders[nationalId] = createNotificationBuilder(this)
            startForeground(nationalId, builders[nationalId]?.build())
            FileDownloader().downloadFile(url, fileName) { progress ->
                _queueState.update {
                    it[nationalId] = progress
                    builders[nationalId]?.apply {
                        setProgress(100, progress, false)
                        setContentTitle("$progress %")
                        setContentText("file id: $nationalId")
                        notificationManager.notify(nationalId, build())
                    }
                    it
                }
                if (progress == 100) {
                    notificationManager.cancel(nationalId)
                    builders.remove(nationalId)
                    if (_queueState.value.isEmpty()) {
                        stopSelf()
                    }
                }
            }
        }
    }





    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.setSound(null, null)
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onBind(intent: Intent?) = binder
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) = START_STICKY
    inner class LocalBinder: Binder() { fun getService(): DownloadService = this@DownloadService }
    companion object{
        const val CHANNEL_ID: String = "CHANNEL_ID"
        const val CHANNEL_NAME: String = "CHANNEL_NAME"
    }
}
