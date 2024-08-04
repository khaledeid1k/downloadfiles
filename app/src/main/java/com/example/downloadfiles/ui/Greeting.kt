package com.example.downloadfiles.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CUSTOM_ACTION
import com.example.downloadfiles.PROGRESS_EXTRA
import com.example.downloadfiles.R
import com.example.downloadfiles.SharedDataHolder
import com.example.downloadfiles.SharedDataHolder.LocalActivity
import com.example.service.DownloadService


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val baseViewModel = SharedDataHolder.baseViewModel
    //val updateNotificationProcess = baseViewModel.updateNotificationProcess.collectAsState()
    val progressState = baseViewModel?.progressState?.collectAsState()
    val context = LocalContext.current
    val activity = LocalActivity.current

    val progress = remember {
        mutableIntStateOf(0)
    }



    val audioLoopBoundServiceIntent: Intent by lazy {
        Intent(
            context,
            DownloadService::class.java
        )
    }

    var myLoopBoundService: DownloadService?


    val boundServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                val binder = service as DownloadService.MyLoopServiceBinder
                myLoopBoundService = binder.getService()
                myLoopBoundService?.setCallback(object : DownloadService. ServiceCallback{
                    override fun onResultReceived(result: Int) {
                        progress.intValue = result
                    }
                })
            }

            override fun onServiceDisconnected(arg0: ComponentName) {
                myLoopBoundService = null
            }
        }
    }

    LaunchedEffect(Unit) {
        //activity.bindService(audioLoopBoundServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            //activity.unbindService(boundServiceConnection)
        }
    }


    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
//                val intentb =  Intent(CUSTOM_ACTION)
//                intentb.putExtra(PROGRESS_EXTRA,20)
//                context.sendBroadcast(intentb)
                val intents = Intent(context, DownloadService::class.java)
                context.startForegroundService(intents)
                //activity.bindService(audioLoopBoundServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
            },
            modifier = modifier
        ) {

            Text(text = "Download")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (progressState?.value == 100) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.done),
                contentDescription = ""
            )
        } else {
            Box {
                CircularProgressIndicator(
                    trackColor = androidx.compose.ui.graphics.Color.Red,
                    progress = progressState?.value?.toFloat()?:-1.0f,
                    //progress = progress.intValue.toFloat(),
                    //progress = updateNotificationProcess.value.toFloat(),
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = progressState?.value.toString(),
                    //text = progress.intValue.toString(),
                    //text = updateNotificationProcess.value.toString(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}