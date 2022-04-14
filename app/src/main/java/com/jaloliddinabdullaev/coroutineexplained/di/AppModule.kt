package com.jaloliddinabdullaev.coroutineexplained.di

import android.app.Application
import androidx.room.Room
import com.jaloliddinabdullaev.coroutineexplained.api.ApiInterface
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://testone.uz/jaloliddin1199/MemeMaker/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideImageApi(retrofit: Retrofit): ApiInterface
    = retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app:Application) : ImageDatabase =
        Room.databaseBuilder(app, ImageDatabase::class.java, "image_database").allowMainThreadQueries()
            .build()

}