package com.jaloliddinabdullaev.coroutineexplained.data.local.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_list")
data class ImageItem (
    @PrimaryKey
    val id:String,
    val image:String
)