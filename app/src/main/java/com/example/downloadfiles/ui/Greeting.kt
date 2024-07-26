package com.example.downloadfiles.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.downloadfiles.R
import com.example.downloadfiles.completeNotification
import com.example.downloadfiles.createNotificationChannel
import com.example.downloadfiles.downloadFile
import com.example.downloadfiles.initNotificationChannel
import com.example.downloadfiles.initNotificationManager
import com.example.downloadfiles.network.downloadFileR
import com.example.downloadfiles.updateNotificationProgress

@Preview(showBackground = true)
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var progressValue by remember { mutableIntStateOf(0) }
    var completed by remember { mutableIntStateOf(0) }
    val  context= LocalContext.current
    initNotificationManager(context)
    initNotificationChannel()
    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                createNotificationChannel(context,"sasssssdsss")
                downloadFileR(
                    //   "https://images.pexels.com/photos/35537/child-children-girl-happy.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                    "https://server8.mp3quran.net/harthi/061.mp3",
              //      "https://server8.mp3quran.net/harthi/001.mp3",

                    "ssasdasss",
                    {
                    //   updateNotificationProgress(context,it)
                        progressValue = it
                    },
                    {

                     completeNotification(context)
                        completed = it
                    })
            },
            modifier = modifier
        ) {
            Text(text = "Download")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (completed > 0) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.done),
                contentDescription = ""
            )
        } else {
            Box {
                CircularProgressIndicator(
                    trackColor = androidx.compose.ui.graphics.Color.Red,
                    progress = progressValue.toFloat(),
                    modifier = Modifier.size(100.dp)
                )
                Text(text = progressValue.toString(), modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}