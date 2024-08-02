package com.example.downloadfiles.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.downloadfiles.CUSTOM_ACTION
import com.example.downloadfiles.PROGRESS_EXTRA

class AppRemovalReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("afsdfsdfsd", " onReceive(context: Context?, intent: Intent?) : ")
        if (intent?.action == CUSTOM_ACTION) {
            Log.d("afsdfsdfsd", " TEST_CUSTOM_ACTION ")
            val progress = intent.getIntExtra(PROGRESS_EXTRA, 0)

        }else{
            Log.d("afsdfsdfsd", " TEST_CUSTOM_ACTION else ")
        }
    }
}