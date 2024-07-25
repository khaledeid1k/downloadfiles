package com.example.downloadfiles.network

import android.os.Environment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import retrofit2.Callback
@OptIn(DelicateCoroutinesApi::class)
fun downloadFileR(url: String, hadithBookName: String, progressOfDownload: (Int) -> Unit, completedDownload: (Int) -> Unit) {
    GlobalScope.launch(Dispatchers.IO) {
        val call = RetrofitClient.service.downloadFile(url)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        val fileSize = responseBody.contentLength()
                        val inputStream: InputStream = responseBody.byteStream()
                        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "$hadithBookName.JPG")
                        val outputStream = FileOutputStream(file)

                        val buffer = ByteArray(1024)
                        var byteCopied: Long = 0
                        var bytes: Int

                        while (inputStream.read(buffer).also { bytes = it } != -1) {
                            byteCopied += bytes
                            val progress = (byteCopied.toFloat() / fileSize * 100).toInt()
                            progressOfDownload(progress)
                            outputStream.write(buffer, 0, bytes)
                            if (progress == 100) {
                                completedDownload(progress)
                                break
                            }
                        }

                        outputStream.close()
                        inputStream.close()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
            }
        })
    }
}