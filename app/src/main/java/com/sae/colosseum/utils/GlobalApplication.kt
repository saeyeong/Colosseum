package com.sae.colosseum.utils

import android.app.Application
import android.util.Log
import com.sae.colosseum.model.entity.UserEntity

class GlobalApplication : Application() {

    companion object {
        lateinit var prefs: SharedPreferences
        lateinit var instance: GlobalApplication
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferences(applicationContext)
        instance = this
    }
}