package com.example.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CUSTOM_ACTION
import com.example.downloadfiles.NOTIFICATION_ID
import com.example.downloadfiles.PROGRESS_EXTRA
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.createNotificationChannel
import com.example.downloadfiles.network.DownloadFileR
import com.example.downloadfiles.notifyNotification
import com.example.downloadfiles.ui.AppRemovalReceiver
import com.example.downloadfiles.updateNotificationProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DownloadService : Service() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, createNotificationChannel(this, "dsdd"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateProgress()
        return START_STICKY
    }


    private fun updateProgress() {
        DownloadFileR().downloadFile(
            progressTrack = {progress->
                Log.d("afsdfsdfsd", " updateProgress(progress: Int) : $progress")
                updateNotificationProgress(progress)
                notifyNotification()
                if (progress <= 100) {
                    stopForeground(true)
                    stopSelf()
                }

            }
        )


        }

    override fun onDestroy() {
       // unregisterReceiver(appRemovalReceiver)
        super.onDestroy()
    }
    override fun onBind(intent: Intent?) = null


}
