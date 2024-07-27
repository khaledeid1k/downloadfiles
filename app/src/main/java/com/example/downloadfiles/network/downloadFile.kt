package com.example.downloadfiles.network

import android.os.Environment
import android.util.Log
import com.example.downloadfiles.BaseViewModel
import com.example.downloadfiles.SharedDataHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DownloadFileR{
    private var baseViewModel: BaseViewModel = SharedDataHolder.baseViewModel
    fun downloadFile(completedDownload: (Int) -> Unit, progressTrack:(Int)->Unit) {
       CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("TAasdasdsadsadG", "downloadFile: ")
                val response = RetrofitClient.service.downloadFile("https://server8.mp3quran.net/harthi/014.mp3")
                response.let { responseBody ->
                    val fileSize = responseBody.contentLength()
                    val inputStream: InputStream = responseBody.byteStream()
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "sadad5165.mp3"
                    )
                    val outputStream = FileOutputStream(file)

                    val buffer = ByteArray(1024)
                    var byteCopied: Long = 0
                    var bytes: Int

                    while (inputStream.read(buffer).also { bytes = it } != -1) {
                        byteCopied += bytes
                        val progress = (byteCopied.toFloat() / fileSize * 100).toInt()
                        progressTrack(progress)
                        baseViewModel.updateNotificationProcess.update {
                            Log.d("TAGasdsddssd", "Greeting:$it} ")
                            progress
                        }

                        outputStream.write(buffer, 0, bytes)
                        if (progress == 100) {
                            completedDownload(progress)
                            break
                        }
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


