package com.app.chenyang.ussettings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.chenyang.ussettings.utils.Item
import com.app.chenyang.ussettings.utils.MyConst
import com.app.chenyang.ussettings.utils.SettingsUtils
import java.io.*


class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startIntent = Intent(this,ListActivity::class.java)
        startIntent.putExtra(MyConst.LIST_FILE,SettingsUtils.getSettingsList())
        startActivity(startIntent)
        finish()
    }
}
