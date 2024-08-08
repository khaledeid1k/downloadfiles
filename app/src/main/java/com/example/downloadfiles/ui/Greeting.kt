package com.example.downloadfiles.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.example.downloadfiles.R
import com.example.downloadfiles.SharedDataHolder
import com.example.service.DownloadService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Preview
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var mService by remember { mutableStateOf<DownloadService?>(null) }
    var mBound by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val updateNotificationProcess: MutableMap<Int, MutableState<Int> > =
        remember { mutableMapOf() }


    DisposableEffect(key1 = Unit) {
        val connection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName, service: IBinder) {
                val binder = service as DownloadService.LocalBinder
                mService = binder.getService()
                mBound = true
            }

            override fun onServiceDisconnected(arg0: ComponentName) {
                mBound = false
            }
        }
        Intent(context, DownloadService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        onDispose {
            context.unbindService(connection)
            mBound = false
        }
    }
//
//    LaunchedEffect(key1 = mService?.stateFlows) {
//        if (mBound) {
//            mService?.updateNotificationProcess?.collectLatest {
//                mService?.let { mService ->
//                    Log.d("adsad3663", "mService $mService")
//                    Log.d("adsad3663", "stateFlows ${mService.stateFlows[1]}")
//                    mService.stateFlows[1]?.collectLatest {
//                        Log.d("adsad3663", "Collected value: $it")
//                    }
//                } ?: Log.d("adsad3663", "mService = null")
//            }
//        }
//    }


    LaunchedEffect(key1 = mService?.stateFlows) {
        if (mBound) {
            mService?.updateNotificationProcess?.collectLatest {
                mService?.stateFlows?.get(1)?.collectLatest {lol->
                    updateNotificationProcess[1]?.let {
                        it.value = lol
                    } ?:Log.d("adsadddddddda44sdsad", "1 SharedDataHolder:  updateNotificationProcess[1] = null   ")
                    Log.d("adsadddddddda44sdsad", "1 SharedDataHolder:${it}  ")
                    Log.d("adsadddddddda44sdsad", "1 SharedDataHolder:${ updateNotificationProcess[1]?.value}  ")

                }
            }
        }

    }
//    LaunchedEffect(key1 = mService?.stateFlows) {
//        Log.d("adsadddddddda44sdsad", "2 SharedDataHolder:$ aaaa ")
//
//        if (mBound) {
//            Log.d("adsadddddddda44sdsad", "2 SharedDataHolder:$ mBound ")
//             mService?.stateFlows?.get(2)?.collectLatest {
//                Log.d("adsadddddddda44sdsad", "2 SharedDataHolder:${it}  ")
//
//            }
//        }
//
//
//    }
//    LaunchedEffect(key1 =mService?.stateFlows) {
//        Log.d("adsadddddddda44sdsad", "3 SharedDataHolder:$ aaaa ")
//
//        if (mBound) {
//            Log.d("adsadddddddda44sdsad", "3 SharedDataHolder:$ mBound ")
//
//            mService?.stateFlows?.get(3)?.collectLatest {
//                Log.d("adsadddddddda44sdsad", "3 SharedDataHolder:${it}  ")
//
//            }
//        }
//    }





    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
        Alignment.CenterHorizontally
    ) {
        updateNotificationProcess[1] = remember {
            mutableIntStateOf (0)
        }
        DownloadFile(
            updateNotificationProcess[1]?.value?:0,
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            1,
            { url, nationalId ->
                mService?.startDownload("ssssadsssss", url, nationalId)
            }) { key ->
//            LaunchedEffect(key1 = mService?.stateFlows?.get(key)) {
//                Log.d("TAG", "saadsadsad:${updateNotificationProcess[1]?.value} , $key")
//                if (mBound) {
//                    mService?.stateFlows?.get(1)?.collectLatest {
//                        updateNotificationProcess[1]?.value = it
//                    }
//                } else {
//
//
//                }
//
//            }
        }
//        DownloadFile(updateNotificationProcess[2]?.value ?: 0,
//            context,
//            "https://server8.mp3quran.net/harthi/015.mp3",
//            2,
//            { url, nationalId ->
//                mService?.startDownload("2d1a", url, nationalId)
//            }
//        ) { key ->
//            LaunchedEffect(key1 = mService?.stateFlows?.get(2)) {
//                Log.d("TAG", "saadsadsad:${updateNotificationProcess[key]?.value} , $key")
//
//                if (mBound) {
//                    mService?.stateFlows?.get(2)?.collectLatest {
//                        updateNotificationProcess[2]?.value = it
//                    }
//                } else {
//
//
//                }
//
//            }
//        }
//        DownloadFile(updateNotificationProcess[3]?.value ?: 0,
//            context,
//            "https://server8.mp3quran.net/harthi/016.mp3",
//            3,
//            { url, nationalId ->
//                mService?.startDownload("3d1a", url, nationalId)
//            }
//        ) { key ->
//
//            LaunchedEffect(key1 = mService?.stateFlows?.get(key)) {
//                Log.d("TAG", "saadsadsad:${updateNotificationProcess[key]?.value} , $key")
//
//                if (mBound) {
//                    mService?.stateFlows?.get(key)?.collectLatest {
//                        updateNotificationProcess[key]?.value = it
//                    }
//                } else {
//
//
//                }
//
//            }
//        }


    }

}

@Composable
fun DownloadFile(
    progress: Int,
    context: Context,
    url: String,
    nationalId: Int,
    startDownload: (String, Int) -> Unit,
    updateState: @Composable (Int) -> Unit
) {
    Log.d("TAG", "asdsa4d58sa4d: $progress")

    updateState(nationalId)
    Button(
        onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intents = Intent(context, DownloadService::class.java)
                context.startForegroundService(intents)
                startDownload(url, nationalId)

            }
        },
    ) {

        Text(text = "Download")
    }
    Spacer(modifier = Modifier.height(16.dp))
    if (progress == 100) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.done),
            contentDescription = ""
        )
    } else {
        Box {
            Log.d("TAG", "asdsa4d58sa4d: $progress")

            CircularProgressIndicator(
                trackColor = androidx.compose.ui.graphics.Color.Red,
                progress = progress.toFloat(),
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = progress.toString(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

