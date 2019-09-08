package com.elnemr.introslider

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    var layouts = arrayOf(
        R.layout.first_slide,
        R.layout.second_slide,
        R.layout.third_slide,
        R.layout.fourth_slide
    )
    lateinit var myPagerAdapter: MyPagerAdapter
    // lateinit var dotsLayouts: LinearLayout
    //lateinit var dots: Array<ImageView>

    val dots = arrayOfNulls<ImageView?>(layouts.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

//        dots = Array<ImageView>(4, { ImageView(this, this) })
//
//        dots?.size ?: layouts.size

        myPagerAdapter = MyPagerAdapter(layouts, this)
        viewPager.adapter = myPagerAdapter

        creatDots(0)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                creatDots(position)
            }

        })
    }

    fun creatDots(currentPosition: Int) {
        if (dotsLayout != null)
            dotsLayout.removeAllViews()

        for (i in layouts) {
            dots[i]= ImageView(this)
            if (i == currentPosition) {
                dots[i]?.setImageDrawable(resources.getDrawable(R.drawable.active_dots))
            } else {
                dots[i]?.setImageDrawable(resources.getDrawable(R.drawable.inactive_dots))
            }

            var params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            params.setMargins(4, 0, 4, 0)

            dotsLayout.addView(dots[i], params)

        }
    }
}