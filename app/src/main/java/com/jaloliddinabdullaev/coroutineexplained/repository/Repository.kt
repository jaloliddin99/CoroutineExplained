package com.jaloliddinabdullaev.coroutineexplained.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import com.jaloliddinabdullaev.coroutineexplained.api.ApiInterface
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.ImageDatabase
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.NoteItem
import com.jaloliddinabdullaev.coroutineexplained.utils.networkBoundResource
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val db: ImageDatabase
) {

    private val imageDao = db.imageDoa()


    suspend fun getAllImages2()=apiInterface.getAllImages2()

    fun getAllNotes():LiveData<List<NoteItem>> = imageDao.getAllNotes().asLiveData()
    suspend fun insertNote(noteItem: NoteItem){
        imageDao.insertNote(noteItem)
    }

    suspend fun deleteNote(id:Int){
        imageDao.deleteNote(id)
    }

    suspend fun updateNote(title:String, description:String, date: Date, id: Int){
        imageDao.updateNote(title, description, date, id)
    }

    fun getAllImages() = networkBoundResource(
        query = {
                imageDao.getAllImage()
        },
        fetch = {
            apiInterface.getAllImages()
        },
        saveFetchResult = {
            db.withTransaction {
                imageDao.deleteAllImage()
                imageDao.insertImage(it)
            }
        }
    )

}