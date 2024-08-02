package com.example.downloadfiles.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CUSTOM_ACTION
import com.example.downloadfiles.CheckPermissions
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.ui.theme.DownloadfilesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //initNotificationManager(this)
        //initNotificationChannel()
//        val receiver = AppRemovalReceiver()
//        val filter = IntentFilter(CUSTOM_ACTION)
       // registerReceiver(receiver,filter, RECEIVER_NOT_EXPORTED)
        setContent {
            DownloadfilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val baseViewModel: BaseViewModel = hiltViewModel()
                    SharedDataHolder.baseViewModel = baseViewModel

                    CheckPermissions(this)

                    Greeting(modifier = Modifier.padding(innerPadding),baseViewModel)
                }
            }
        }
    }
}




