package com.elnemr.introslider

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    var layouts = arrayOf(
        R.layout.first_slide,
        R.layout.second_slide,
        R.layout.third_slide,
        R.layout.fourth_slide
    )
    lateinit var myPagerAdapter: MyPagerAdapter
    lateinit var dots: Array<ImageView>
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        preferenceManager = PreferenceManager(this)
        if (preferenceManager.checkPref()) {
            loadMainActivity()
        }


        if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        dots = Array<ImageView>(4) { ImageView(this) }
        btnSkip.setOnClickListener(this)
        btnNext.setOnClickListener(this)
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
                if (position == layouts.size-1) {
                    btnNext.text = "Start"
                    btnSkip.visibility = View.INVISIBLE
                } else {
                    btnNext.text = "Next"
                    btnSkip.visibility = View.VISIBLE
                }
            }

        })
    }

    fun creatDots(currentPosition: Int) {
        if (dotsLayout != null)
            dotsLayout.removeAllViews()

        for (i in 0..layouts.size - 1) {
            Log.d("MMM", " $i")
            dots[i] = ImageView(this)
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

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btnNext -> {
                loadnextSlide()

            }
            R.id.btnSkip -> {
                loadMainActivity()
                preferenceManager.writeToPref()
            }
        }
    }

    fun loadMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun loadnextSlide() {

        var nextSlide: Int = viewPager.currentItem + 1
        if (nextSlide < layouts.size) {
            viewPager.setCurrentItem(nextSlide)
        } else {
            loadMainActivity()
            preferenceManager.writeToPref()
        }
    }


}