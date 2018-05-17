package com.app.chenyang.ussettings.helper

import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by chenyang on 2017/8/9.
 */
open class OnRecycleItemTouchListener ():GestureDetector.SimpleOnGestureListener(),RecyclerView.OnItemTouchListener{
    var recyclerView:RecyclerView? = null
    var gestureDetector:GestureDetector? = null

    constructor(recyclerView: RecyclerView):this(){
        this.recyclerView = recyclerView
        gestureDetector = GestureDetector(recyclerView.context,this)
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        gestureDetector!!.onTouchEvent(e)

    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        gestureDetector!!.onTouchEvent(e)
        return true
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }


    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        val child = recyclerView!!.findChildViewUnder(e!!.x, e.y)
        if (child != null) {
            val vh = recyclerView!!.getChildViewHolder(child)
            onItemClick(vh, recyclerView!!.getChildAdapterPosition(child))
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        super.onLongPress(e)
        var child = recyclerView!!.findChildViewUnder(e!!.x, e.y)
        if (child != null) {
            val vh = recyclerView!!.getChildViewHolder(child)
            onItemLongPress(vh, recyclerView!!.getChildAdapterPosition(child))
        }
    }

    open fun onItemLongPress(vh: RecyclerView.ViewHolder, position: Int) {}
    open fun onItemClick(vh: RecyclerView.ViewHolder, position: Int) {}
}