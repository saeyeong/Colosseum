package com.sae.coloseum.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPreferencesActivity(context: Context) : AppCompatActivity() {
    private val PREFS_FILENAME = "prefs"
    private val PREF_KEY_MY_EDITTEXT = "myEditText"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var myEditText: String?
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()
}