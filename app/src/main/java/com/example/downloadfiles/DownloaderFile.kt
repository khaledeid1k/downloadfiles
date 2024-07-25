package com.example.downloadfiles

import android.os.Environment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

    @OptIn(DelicateCoroutinesApi::class)
    fun downloadFile(url: String, hadithBookName: String, progressOfDownload:(Int)->Unit, completedDownload:(Int)->Unit): Long {
        GlobalScope.launch(Dispatchers.IO) {
            val url= URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod="GET"
            connection.setRequestProperty("Accept-Encoding","identity")
            connection.connect()
            if(connection.responseCode  in 200..299){
                val fileSize = connection.contentLength

                val inputStream = connection.inputStream
                val file= File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "$hadithBookName.mp3"
                )
                val fileOutputStream = FileOutputStream(file)
                var byteCopied:Long=0
                val buffer = ByteArray(1024)
                var bytes = inputStream.read(buffer)
                while (bytes>=0){
                    byteCopied+=bytes
                    val progress = (byteCopied.toFloat() / fileSize.toFloat() * 100).toInt()
                    progressOfDownload(progress)
                    fileOutputStream.write(buffer,0,bytes)
                    bytes = inputStream.read(buffer)
                    if (progress==100){
                        completedDownload(progress)
                    }
                }
                fileOutputStream.close()
                inputStream.close()



            }

        }
        return 1L
    }



