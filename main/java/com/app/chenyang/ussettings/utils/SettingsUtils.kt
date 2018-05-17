package com.app.chenyang.ussettings.utils

import android.content.Context
import android.content.res.XmlResourceParser
import android.provider.Settings
import android.text.TextUtils
import android.widget.Toast
import java.io.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by chenyang on 2017/8/7.
 */
class SettingsUtils {
    companion object {
        val toast: Toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);

        val settingsContext: Context = getContext()!!.createPackageContext(MyConst.SETTINGS_PACKAGE_NAME, Context.CONTEXT_IGNORE_SECURITY)

        fun getContext() = MyApp.mContext

        fun getMainThreadId() = MyApp.mainThread

        fun getHandler() = MyApp.handler

        fun isRunOnUiThread() = MyApp.mainThread == android.os.Process.myTid()

        fun runOnUiThread(runnable: Runnable) {
            if (isRunOnUiThread()) {
                runnable.run()
            } else {
                getHandler()!!.post(runnable)
            }
        }

        fun showToast(a: Any) {
            runOnUiThread(Runnable {
                when (a) {
                    is Int -> toast.setText(a)
                    is String -> toast.setText(a)
                }
                toast.show()
            })
        }

        fun getTargetString(name: String) = settingsContext.getString(settingsContext.resources.getIdentifier(name, MyConst.RES_STRING, MyConst.SETTINGS_PACKAGE_NAME))

        fun getTargetDrawable(name: String) = settingsContext.getDrawable(settingsContext.resources.getIdentifier(name, MyConst.RES_DRAWABLE, MyConst.SETTINGS_PACKAGE_NAME))

        fun xmlParser(): ArrayList<Item> {
            val itemList: ArrayList<Item> = ArrayList()
            val parser = settingsContext.resources.getXml(settingsContext.resources.getIdentifier(MyConst.LIST_XML, MyConst.RES_XML, MyConst.SETTINGS_PACKAGE_NAME))
            while (parser.eventType != XmlResourceParser.END_DOCUMENT) {
                if (parser.eventType == XmlResourceParser.START_TAG) {
                    val tagName = parser.name
                    if (MyConst.XML_TAG.equals(tagName)) {
                        itemList.add(createItem(parser))
                    }
                }
                parser.next()
            }
            return itemList
        }

        fun createItem(parser: XmlResourceParser): Item {
            val map = mutableMapOf<String, String>()
            for (i in 0..parser.attributeCount - 1) {
                map.put(parser.getAttributeName(i), parser.getAttributeValue(i))
            }
            return Item(title = map.getOrDefault(MyConst.ATTR_TITLE, MyConst.XML_DEFAULT),
                    icon = map.getOrDefault(MyConst.ATTR_ICON, MyConst.XML_DEFAULT),
                    packageName = map.getOrDefault(MyConst.ATTR_PACKAGE, MyConst.SETTINGS_PACKAGE_NAME),
                    className = map.getOrDefault(MyConst.ATTR_CLASS, MyConst.XML_DEFAULT),
                    visible = map.getOrDefault(MyConst.ATTR_VISIBLE, MyConst.VISIBLE),
                    switch = map.getOrDefault(MyConst.ATTR_SWITCH, MyConst.XML_DEFAULT))
        }

        fun getIsVisible(value: String): Boolean {
            if (TextUtils.isEmpty(value))
                return true;
            else
                return Settings.System.getInt(getContext()!!.contentResolver, value, 0) == 1
        }

        fun isShowItem(item: Item): Boolean {
            return MyConst.VISIBLE.equals(item.visible) && getIsVisible(item.switch)
        }

        fun getSettingsList():ArrayList<Item>{
            val file = File(getContext()!!.filesDir,MyConst.LIST_FILE)
            var list : ArrayList<Item>?
            if (!file.exists()){
                var out: ObjectOutputStream?= null
                list = SettingsUtils.xmlParser()
                try {
                    out = ObjectOutputStream(FileOutputStream(file))
                    out.writeObject(list)
                }catch (e:Exception){
                    if (file.exists())
                        file.delete()
                }finally {
                    if(out != null){
                        out.close()
                    }
                }
            }else{
                var read : ObjectInputStream?  = null;
                try {
                    read = ObjectInputStream(FileInputStream(file))
                    list = read.readObject() as ArrayList<Item>?
                }catch (e:Exception){
                    list = SettingsUtils.xmlParser()
                }finally {
                    if(read != null){
                        read.close()
                    }
                }
            }
            return list!!
        }

        fun filterList(list:ArrayList<Item>):ArrayList<Item>{
            val it = list.iterator()
            while(it.hasNext()){
                if(!isShowItem(it.next())){
                    it.remove()
                }
            }
            return list
        }
    }
}