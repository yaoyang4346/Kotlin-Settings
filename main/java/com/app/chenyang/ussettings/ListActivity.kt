package com.app.chenyang.ussettings

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.Window
import com.app.chenyang.ussettings.helper.DividerItemDecoration
import com.app.chenyang.ussettings.helper.SettingsListAdapter
import com.app.chenyang.ussettings.helper.SettingsListViewHolder
import com.app.chenyang.ussettings.utils.Item
import com.app.chenyang.ussettings.utils.LogUtils
import com.app.chenyang.ussettings.utils.MyConst
import com.app.chenyang.ussettings.utils.SettingsUtils
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity() {
    val observerMap = mutableMapOf<String,ContentObserver>()
    var list : ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        list = intent.getSerializableExtra(MyConst.LIST_FILE) as ArrayList<Item>

        if (list == null){
            SettingsUtils.showToast(R.string.error_mag)
            finish()
        }
        list!!.filterNot { TextUtils.isEmpty(it.switch) }
                .forEach { observerMap.put(it.switch, createContentObserver()) }
        rc.layoutManager=LinearLayoutManager(SettingsUtils.getContext())
        rc.adapter = SettingsListAdapter(SettingsUtils.filterList(list!!), object : SettingsListViewHolder.ItemOnClickListener{
            override fun onclick(view: View, position: Int) {
                val intent = Intent()
                val item = list!![position]
                if(MyConst.SETTINGS_PACKAGE_NAME.equals(item.packageName)){
                    intent.putExtra(MyConst.INTENT_EXTRA,item.packageName+"."+item.className)
                    intent.setClassName(item.packageName,item.packageName+"."+MyConst.SETTINGS_CLASS_NAME)
                }else{
                    intent.setClassName(item.packageName,item.packageName+"."+item.className)
                }
                try{
                    startActivity(intent)
                }catch (e:Exception){
                    SettingsUtils.showToast(R.string.start_error)
                }

            }
        })
        rc.addItemDecoration(DividerItemDecoration(SettingsUtils.getContext()!!,getDrawable(R.drawable.recycle_divider)))
        registerObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterObserver()
    }

    fun registerObserver(){
        observerMap.forEach{
            LogUtils.d("registerObserver: "+it.key)
            this.contentResolver.registerContentObserver(Settings.System.getUriFor(it.key),true,it.value)
        }
    }
    fun unregisterObserver(){
        observerMap.forEach{
            LogUtils.d("unregisterObserver: "+it.key)
            this.contentResolver.unregisterContentObserver(it.value)
        }
    }

    fun createContentObserver():ContentObserver{
        return object : ContentObserver(Handler()){
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                val adapter = rc.adapter as SettingsListAdapter
                list = SettingsUtils.getSettingsList()
                adapter.updateList(SettingsUtils.filterList(list!!))
            }
        }
    }
}
