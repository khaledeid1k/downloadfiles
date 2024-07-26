package com.example.downloadfiles.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface FileService {
    @GET
    @Streaming
   suspend fun downloadFile(@Url fileUrl: String):ResponseBody
}