package com.example.downloadfiles.network

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.CUSTOM_ACTION
import com.example.downloadfiles.PROGRESS_EXTRA
import com.example.downloadfiles.SharedDataHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DownloadFileR{
    private var baseViewModel: BaseViewModel = SharedDataHolder.baseViewModel
    fun downloadFile( progressTrack:(Int)->Unit) {
      CoroutineScope(Dispatchers.IO) .launch{
           try {
               val response =
                   RetrofitClient.service.downloadFile("https://server8.mp3quran.net/harthi/014.mp3")
               response.let { responseBody ->
                   val fileSize = responseBody.contentLength()
                   val inputStream: InputStream = responseBody.byteStream()
                   val file = File(
                       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                       "4864jnjn.mp3"
                   )
                   val outputStream = FileOutputStream(file)

                   val buffer = ByteArray(1024)
                   var byteCopied: Long = 0
                   var bytes: Int

                   while (inputStream.read(buffer).also { bytes = it } != -1) {
                       byteCopied += bytes
                       val progress = (byteCopied.toFloat() / fileSize * 100).toInt()
                       Log.d("afsdfsdfsd", "updatePreogress: $progress")
                        baseViewModel.updateNotificationProcess.update {
                            progress
                        }

                       outputStream.write(buffer, 0, bytes)

                       progressTrack(progress)
                   }

                   outputStream.close()
                   inputStream.close()
               }
           } catch (e: Exception) {
               Log.d("TAG", "downloadFileR:$e ")

           }


       }
   }



}


