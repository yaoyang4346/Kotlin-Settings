package com.app.chenyang.ussettings.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by chenyang on 2017/8/9.
 */
class DividerItemDecoration() : RecyclerView.ItemDecoration() {
    val mOrientation : Int = LinearLayoutManager.VERTICAL
    var mDivider : Drawable? = null

    constructor(context:Context, mDivider:Drawable) : this(){
        this.mDivider = mDivider
    }


    override fun onDraw(c: Canvas?, parent: RecyclerView?) {
        super.onDraw(c, parent)
        drawVertical(c!!,parent)
    }

    fun drawVertical(canvas: Canvas,parent: RecyclerView?){
        val left = parent!!.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0..childCount-2){
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left + 10, top, right - 10, bottom)
            mDivider!!.draw(canvas)
        }
    }
}