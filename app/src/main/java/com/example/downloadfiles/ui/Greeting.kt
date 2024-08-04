package com.example.downloadfiles.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.downloadfiles.R
import com.example.service.DownloadService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val serviceIntent = remember { Intent(context, DownloadService::class.java) }
    val connection = remember { TimerServiceConnection() }
    val service = connection.service.collectAsState()
    LaunchedEffect(Unit) {
        context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                context.startService(serviceIntent)
                service.value?.startDownload()
            },
            modifier = modifier
        ) {

            Text(text = "Download")
        }
        Spacer(modifier = Modifier.height(16.dp))

        service.value?.let {
            val progressRemaining by it.progressState.collectAsState()
            if (progressRemaining == 100) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.done),
                    contentDescription = ""
                )
            } else {
                Box {
                    CircularProgressIndicator(
                        trackColor = androidx.compose.ui.graphics.Color.Red,
                        progress = progressRemaining.toFloat(),
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = progressRemaining.toString(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            context.unbindService(connection)
        }
    }
}

class TimerServiceConnection : ServiceConnection {
    private var _service = MutableStateFlow<DownloadService?>(null)
    var service = _service.asStateFlow()

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        this._service.value = (service as DownloadService.LocalBinder).getService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        _service.value = null
    }
}