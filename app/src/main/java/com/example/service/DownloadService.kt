package com.example.service

import android.app.Service
import android.content.Intent
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
@AndroidEntryPoint
class DownloadService : Service() {
    private val baseViewModel: BaseViewModel
        get() = SharedDataHolder.baseViewModel

    override fun onCreate() {
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            baseViewModel.updateNotificationProcess.collectLatest {
                updateNotificationProgress(it)
            }
        }

        DownloadFileR(baseViewModel).downloadFile{
            stopService(intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?) = null


    override fun onDestroy() {
        super.onDestroy()
    }

}