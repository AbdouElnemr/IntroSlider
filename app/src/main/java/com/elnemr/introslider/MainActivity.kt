package com.elnemr.introslider

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loadSlides(view: View) {
        PreferenceManager(this).clearSharedPref()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
}