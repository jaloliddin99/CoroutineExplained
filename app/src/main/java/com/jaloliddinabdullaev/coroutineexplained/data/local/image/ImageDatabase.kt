package com.jaloliddinabdullaev.coroutineexplained.data.local.image

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaloliddinabdullaev.coroutineexplained.utils.DateConverter

@Database(entities = [ImageItem::class, NoteItem::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ImageDatabase:RoomDatabase() {
    abstract fun imageDoa(): ImageDao
}