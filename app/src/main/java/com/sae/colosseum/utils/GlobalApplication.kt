package com.sae.colosseum.utils

import android.app.Application
import com.sae.colosseum.model.entity.UserEntity

class GlobalApplication : Application() {

    companion object{
        lateinit var prefs : SharedPreferences
        lateinit var loginUser : UserEntity
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreferences(applicationContext)
    }
}