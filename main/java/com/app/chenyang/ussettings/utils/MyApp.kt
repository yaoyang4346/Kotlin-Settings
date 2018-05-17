package com.app.chenyang.ussettings.utils

import android.app.Application
import android.content.Context
import android.os.Handler

/**
 * Created by chenyang on 2017/8/7.
 */

class MyApp : Application() {
    companion object {
        var mContext: Context? = null
            private set
        var mainThread: Int = 0
            private set
        var handler: Handler? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        mainThread = android.os.Process.myTid()
        handler = Handler()
    }

}
