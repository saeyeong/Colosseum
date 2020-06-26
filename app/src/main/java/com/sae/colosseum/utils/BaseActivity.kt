package com.sae.colosseum.utils

import androidx.appcompat.app.AppCompatActivity
import com.sae.colosseum.network.ServerClient

open class BaseActivity : AppCompatActivity() {

    val token = GlobalApplication.prefs.userToken
    val serverClient: ServerClient = ServerClient()
}