package com.app.chenyang.ussettings.utils

import android.os.Parcelable
import java.io.Serializable

/**
 * Created by chenyang on 2017/8/7.
 */
data class Item(var title: String, var icon: String,
                var packageName: String, var className: String,
                var visible: String, var switch: String) :Serializable{
}