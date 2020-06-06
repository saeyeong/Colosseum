package com.sae.colosseum.utils

import android.app.Application

class GlobalApplication : Application() {

    companion object{
        lateinit var prefs : SharedPreferencesActivity
        lateinit var userNickname : String
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferencesActivity(applicationContext)
    }
}