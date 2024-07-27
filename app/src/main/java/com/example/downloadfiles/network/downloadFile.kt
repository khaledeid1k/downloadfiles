package com.example.downloadfiles.network

import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import com.example.downloadfiles.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class DownloadFileR{
    @Inject
    lateinit var baseViewModel: BaseViewModel
    fun downloadFile(completedDownload: (Int) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Log.d("TAasdasdsadsadG", "downloadFile: ")
                val response = RetrofitClient.service.downloadFile("https://server8.mp3quran.net/harthi/014.mp3")
                response.let { responseBody ->
                    val fileSize = responseBody.contentLength()
                    val inputStream: InputStream = responseBody.byteStream()
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "adadasdasdddddddddddd.mp3"
                    )
                    val outputStream = FileOutputStream(file)

                    val buffer = ByteArray(1024)
                    var byteCopied: Long = 0
                    var bytes: Int

                    while (inputStream.read(buffer).also { bytes = it } != -1) {
                        byteCopied += bytes
                        val progress = (byteCopied.toFloat() / fileSize * 100).toInt()
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


