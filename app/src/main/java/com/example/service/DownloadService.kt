package com.example.service

import android.app.Service
import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.createNotificationChannel
import com.example.downloadfiles.network.DownloadFileR
import com.example.downloadfiles.updateNotificationProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
class DownloadService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        DownloadFileR().downloadFile(
            completedDownload = {  stopService(intent)}, progressTrack = { updateNotificationProgress(it) }
        )
        return START_STICKY
    }

    override fun onBind(intent: Intent?) = null


    override fun onDestroy() {
        super.onDestroy()
    }

}