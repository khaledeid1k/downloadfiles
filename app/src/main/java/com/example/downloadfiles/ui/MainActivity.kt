package com.example.downloadfiles.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CheckPermissions
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.SharedDataHolder.LocalActivity
import com.example.downloadfiles.ui.theme.DownloadfilesTheme
import dagger.hilt.android.AndroidEntryPoint

//val LocalActivity = compositionLocalOf<Activity> { error("no activity") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mBaseViewModel: BaseViewModel

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
                    if (SharedDataHolder.baseViewModel==null){
                        Log.d("messi", "onCreate: baseViewModel == null")
                        SharedDataHolder.baseViewModel = hiltViewModel()
                    }else{
                        Log.d("messi", "onCreate: baseViewModel != null")
                    }


                    CheckPermissions(this)
                    CompositionLocalProvider(
                        LocalActivity provides this
                    ) {
                        Greeting(modifier = Modifier.padding(innerPadding))
                    }


                }
            }
        }
    }
}




