package com.jaloliddinabdullaev.coroutineexplained.ui.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder

class MyService : Service() {

    private val mBinder: IBinder = MyBinder()
    private var mHandler: Handler? = null
    private var mProgress = 0
    private var mMaxValue: Int = 5000
    private var mIsPaused: Boolean? = null



    companion object {
        private const val TAG = "BindService"
    }

    override fun onCreate() {
        super.onCreate()
        mHandler = Handler()
        mProgress = 0
        mIsPaused = true
        mMaxValue = 5000
    }

    fun getIsPaused(): Boolean? {
        return mIsPaused
    }

    fun getProgress(): Int {
        return mProgress
    }

    fun getMaxValue(): Int {
        return mMaxValue
    }


    fun pausePretendedLongWork(){
        mIsPaused = true
    }

    fun unpausePretendedWork(){
        mIsPaused = false
        startPretendedWork()

    }

    private fun startPretendedWork(){
        val runnable:Runnable= object : Runnable{
            override fun run() {
                if (mIsPaused == true || mProgress >=mMaxValue){
                    mHandler?.removeCallbacks(this)
                    pausePretendedLongWork()
                }else{
                    mProgress+=100
                    mHandler?.postDelayed(this, 100)
                }
            }
        }
        mHandler?.postDelayed(runnable, 100)
    }

    fun resetTask(){
        mProgress = 0
    }

    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    inner class MyBinder : Binder() {
        internal val service: MyService get() = this@MyService
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }


}