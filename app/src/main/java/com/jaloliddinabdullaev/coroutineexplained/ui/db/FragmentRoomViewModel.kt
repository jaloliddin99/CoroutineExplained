package com.jaloliddinabdullaev.coroutineexplained.ui.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.NoteItem
import com.jaloliddinabdullaev.coroutineexplained.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FragmentRoomViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val getAllNotes = repository.getAllNotes()

    fun insertNote(noteItem: NoteItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(noteItem)
    }

    fun updateNote(title:String, description:String, date: Date, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(title, description, date, id)
    }

    fun deleteNote(id:Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(id)
    }
}