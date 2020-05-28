package com.sae.coloseum.utils

import android.app.Application

class GlobalApplication : Application() {

    companion object{
        lateinit var prefs : SharedPreferencesActivity
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferencesActivity(applicationContext)
    }
}