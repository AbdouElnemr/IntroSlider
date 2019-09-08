package com.elnemr.introslider

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {
    private   var context: Context
    private lateinit var sharedPref: SharedPreferences

    constructor(context: Context) {
        this.context = context
        getsharedPreferences()
    }

    fun getsharedPreferences() {
        sharedPref = context.getSharedPreferences(
            context.getString(R.string.my_preference),
            Context.MODE_PRIVATE
        )
    }

    fun writeToPref() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(context.getString(R.string.my_preference_key), "INIT_OK")
        editor.commit()
    }

    fun checkPref(): Boolean {
        var status = false
        if (sharedPref.getString(
                context.getString(R.string.my_preference_key),
                "null"
            ).equals("null")
        ) {
            status = false
        } else {
            status = true
        }
        return status
    }

    fun clearSharedPref(){
        sharedPref.edit().clear().commit()
    }

}