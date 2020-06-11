package com.sae.colosseum.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.sae.colosseum.network.ServerClient

open class BaseActivity : AppCompatActivity() {

    val token = GlobalApplication.prefs.myEditText
    val serverClient: ServerClient = ServerClient()
}