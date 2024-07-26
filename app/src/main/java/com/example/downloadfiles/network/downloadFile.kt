package com.example.downloadfiles.network

import android.os.Environment
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.downloadfiles.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@OptIn(DelicateCoroutinesApi::class)
fun downloadFileR(url: String, hadithBookName: String,onProgressCallBack: (Int) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val response = RetrofitClient.service.downloadFile(url)
            response.let { responseBody ->
                val fileSize = responseBody.contentLength()
                val inputStream: InputStream = responseBody.byteStream()
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "$hadithBookName.mp3"
                )
                val outputStream = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var byteCopied: Long = 0
                var bytes: Int

                while (inputStream.read(buffer).also { bytes = it } != -1) {
                    byteCopied += bytes
                    val progress = (byteCopied.toFloat() / fileSize * 100).toInt()
                    //baseViewModel.updateNotificationProcess.update { progress }

                    outputStream.write(buffer, 0, bytes)
                    onProgressCallBack(progress)
                }

                outputStream.close()
                inputStream.close()
            }
        }catch (e:Exception){
            Log.d("TAG", "downloadFileR:$e ")

        }



    }
}