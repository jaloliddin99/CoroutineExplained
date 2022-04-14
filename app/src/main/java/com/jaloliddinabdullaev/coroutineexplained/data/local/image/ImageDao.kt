package com.jaloliddinabdullaev.coroutineexplained.data.local.image

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ImageDao {

    @Query("SELECT * FROM image_list")
    fun getAllImage(): Flow<List<ImageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(humans: List<ImageItem>)

    @Query("DELETE FROM image_list")
    suspend fun deleteAllImage()


    @Query("SELECT * FROM note_list")
    fun getAllNotes() : Flow<List<NoteItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteItem: NoteItem)

    @Query("DELETE FROM note_list WHERE id =:id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE note_list SET title =:title, description =:description, date =:date WHERE id =:id")
    suspend fun updateNote(title:String, description:String, date: Date, id: Int)
}