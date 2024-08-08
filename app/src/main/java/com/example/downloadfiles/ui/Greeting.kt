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
import androidx.compose.material3.Scaffold
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
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
        Alignment.CenterHorizontally
    ) {
        DownloadFile(
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            1,
            mService=mService,
            mBound=mBound
        ) { url, nationalId ->
            mService?.startDownload("45AD1q", url, nationalId)
        }
        DownloadFile(
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            2,
            mService=mService,
            mBound=mBound
        ) { url, nationalId ->
            mService?.startDownload("45AD2q", url, nationalId)
        }
        DownloadFile(
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            3,
            mService=mService,
            mBound=mBound
        ) { url, nationalId ->
            mService?.startDownload("45AD3q", url, nationalId)
        }



    }

}

@Composable
fun DownloadFile(
    context: Context,
    url: String,
    nationalId: Int,
    mService: DownloadService?,
    mBound: Boolean,
    startDownload: (String, Int) -> Unit,

) {
    val progress = remember { mutableIntStateOf (0) }
    LaunchedEffect(key1 = mService?.stateFlows) {
        if (mBound) {
            mService?.updateNotificationProcess?.collectLatest {
                mService.stateFlows[nationalId]?.collectLatest {
                    progress.intValue=it
                }
            }
        }

    }
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
    if (progress.intValue == 100) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.done),
            contentDescription = ""
        )
    } else {
        Box {
            CircularProgressIndicator(
                trackColor = androidx.compose.ui.graphics.Color.Red,
                progress = progress.intValue .toFloat(),
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = progress.intValue.toString(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

