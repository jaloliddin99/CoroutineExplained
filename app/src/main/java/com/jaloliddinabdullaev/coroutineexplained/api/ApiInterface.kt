package com.jaloliddinabdullaev.coroutineexplained.api

import com.jaloliddinabdullaev.coroutineexplained.data.local.image.ImageItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("getHumans.php")
    suspend fun getAllImages():List<ImageItem>

    @GET("getHumans.php")
    suspend fun getAllImages2(): Response<List<ImageItem>>
}