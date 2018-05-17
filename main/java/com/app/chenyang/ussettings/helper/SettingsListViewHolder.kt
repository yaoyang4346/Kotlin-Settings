package com.app.chenyang.ussettings.helper

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.app.chenyang.ussettings.R

/**
 * Created by chenyang on 2017/8/8.
 */

class SettingsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
    var tv: TextView
    var iv: ImageView
    var listener:ItemOnClickListener? = null

    init {
        tv = itemView.findViewById<View>(R.id.tv) as TextView
        iv = itemView.findViewById<View>(R.id.iv) as ImageView
    }

    constructor(listener:ItemOnClickListener,itemView: View):this(itemView){
        this.listener = listener
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (listener!=null){
            listener!!.onclick(p0!!,adapterPosition)
        }
    }

    interface ItemOnClickListener{
        fun onclick(view:View,position:Int)
    }
}
