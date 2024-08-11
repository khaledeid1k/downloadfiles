package com.example.downloadfiles.ui

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.R
import com.example.downloadfiles.SharedDataHolder
import com.example.service.DownloadService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

@Preview
@Composable
fun Greeting(modifier: Modifier = Modifier, viewModel: BaseViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val serviceIntent = remember { Intent(context, DownloadService::class.java) }
    val connection = remember { TimerServiceConnection() }
    val service by connection.service.collectAsState()
    val queue by service?.queueState?.collectAsState() ?: remember {
        mutableStateOf(emptyMap())
    }
    DisposableEffect(key1 = Unit) {
        context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
        onDispose {
            context.unbindService(connection)
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
            service = service,
            queue = queue
        )
        DownloadFile(
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            2,
            service = service,
            queue = queue
        )
        DownloadFile(
            context,
            "https://server8.mp3quran.net/harthi/014.mp3",
            3,
            service = service,
            queue = queue
        )


    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DownloadFile(
    context: Context,
    url: String,
    nationalId: Int,
    service: DownloadService?,
    queue: Map<Int, Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            CircularProgressIndicator(
                trackColor = Color.Gray,
                progress = queue.getOrDefault(nationalId, 0).toFloat(),
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = queue.getOrDefault(nationalId, 0).toString(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!queue.containsKey(nationalId)) {
                        val intents = Intent(context, DownloadService::class.java)
                        context.startForegroundService(intents)
                        service?.startDownload("hgvhgvhghvh", url, nationalId)
                    }
                }
            },
        ) {
            Text(text = "Download")
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

