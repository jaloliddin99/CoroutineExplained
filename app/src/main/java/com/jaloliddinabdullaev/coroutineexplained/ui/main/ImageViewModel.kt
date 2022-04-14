package com.jaloliddinabdullaev.coroutineexplained.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jaloliddinabdullaev.coroutineexplained.data.local.image.ImageItem
import com.jaloliddinabdullaev.coroutineexplained.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: Repository
):ViewModel(){

    val imageList = repository.getAllImages().asLiveData()

    val imageListItems=MutableLiveData<List<ImageItem>>()

    fun imageList()=viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.getAllImages2().let {
                if (it.isSuccessful){
                    imageListItems.postValue(it.body())
                }
            }
        }catch (e:Exception){}

    }

}