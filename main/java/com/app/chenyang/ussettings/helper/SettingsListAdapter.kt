package com.app.chenyang.ussettings.helper

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.chenyang.ussettings.R
import com.app.chenyang.ussettings.utils.Item
import com.app.chenyang.ussettings.utils.SettingsUtils

/**
 * Created by chenyang on 2017/8/8.
 */
class SettingsListAdapter() : RecyclerView.Adapter<SettingsListViewHolder>() {
    var list: ArrayList<Item>? = null
    var listener: SettingsListViewHolder.ItemOnClickListener? = null

    constructor(list: ArrayList<Item>, listener: SettingsListViewHolder.ItemOnClickListener) : this() {
        this.list = list
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SettingsListViewHolder {
        return SettingsListViewHolder(listener!!, LayoutInflater.from(SettingsUtils.getContext()!!).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: SettingsListViewHolder?, position: Int) {
        val item = list!![position]
        holder!!.itemView.tag = 1
        holder.tv.text = SettingsUtils.getTargetString(item.title)
        holder.iv.setImageDrawable(SettingsUtils.getTargetDrawable(item.icon))
    }

    fun updateList(list: ArrayList<Item>) {
        this.list = list
        notifyDataSetChanged()
    }

}