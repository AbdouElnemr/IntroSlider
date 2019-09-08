package com.elnemr.introslider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MyPagerAdapter : PagerAdapter {

    var layoutList = arrayOf<Int>()
    var context: Context
    lateinit var layoutInflater: LayoutInflater


    constructor(layouts: Array<Int>, ctx: Context) {
        this.context = ctx
        this.layoutList = layouts
        layoutInflater = LayoutInflater.from(context)

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view: View = layoutInflater.inflate(layoutList[position], container, false)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        var view:View = `object` as View
        container.removeView(view)
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return layoutList.size
    }
}