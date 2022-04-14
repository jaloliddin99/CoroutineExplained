package com.jaloliddinabdullaev.coroutineexplained.ui.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel : ViewModel() {
    companion object{
        private const val TAG = "FragmentViewModel"
    }

    private val mIsProgressBarUpdating =  MutableLiveData<Boolean>()
    private val mBinder = MutableLiveData<MyService.MyBinder?>()


    private val  serviceConnection=object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "ServiceConnection: connected to service.")
            val binder= p1 as MyService.MyBinder
            mBinder.postValue(binder)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBinder.postValue(null)
            Log.d(TAG, "ServiceConnection: disconnected from service.")

        }

    }


    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    fun getBinder(): LiveData<MyService.MyBinder?> {
        return mBinder
    }


    fun getIsProgressBarUpdating(): LiveData<Boolean?> {
        return mIsProgressBarUpdating
    }

    fun setIsProgressBarUpdating(isUpdating: Boolean) {
        mIsProgressBarUpdating.postValue(isUpdating)
    }

}