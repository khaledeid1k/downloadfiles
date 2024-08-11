package com.example.downloadfiles.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CheckPermissions
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.ui.theme.DownloadfilesTheme
import com.example.service.DownloadService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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




