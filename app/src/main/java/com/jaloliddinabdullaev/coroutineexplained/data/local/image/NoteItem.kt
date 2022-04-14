package com.jaloliddinabdullaev.coroutineexplained.data.local.image

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jaloliddinabdullaev.coroutineexplained.utils.DateConverter
import java.util.*

@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var title:String,
    var description:String,
    @TypeConverters(DateConverter::class)
    val date:Date
){
    constructor():this(0, "", "", Date())
}
