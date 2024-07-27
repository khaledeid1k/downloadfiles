package com.example.downloadfiles.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.downloadfiles.CheckPermissions
import com.example.downloadfiles.initNotificationChannel
import com.example.downloadfiles.initNotificationManager
import com.example.downloadfiles.ui.theme.DownloadfilesTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initNotificationManager(this)
        initNotificationChannel()
        setContent {
            DownloadfilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CheckPermissions(this)
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}




