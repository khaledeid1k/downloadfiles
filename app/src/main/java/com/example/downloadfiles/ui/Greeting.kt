package com.example.downloadfiles.ui

import android.content.Intent
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.R
import com.example.downloadfiles.createNotificationChannel
import com.example.service.DownloadService


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting(modifier: Modifier = Modifier,baseViewModel: BaseViewModel) {
    val updateNotificationProcess = baseViewModel.updateNotificationProcess.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val intent = Intent(context, DownloadService::class.java)
                context.startForegroundService(intent)
            },
            modifier = modifier
        ) {

            Text(text = "Download")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (updateNotificationProcess.value ==100) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.done),
                contentDescription = ""
            )
        } else {
            Box {
                CircularProgressIndicator(
                    trackColor = androidx.compose.ui.graphics.Color.Red,
                    progress = updateNotificationProcess.value.toFloat(),
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = updateNotificationProcess.value.toString(),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}