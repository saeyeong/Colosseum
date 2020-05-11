package com.sae.coloseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class SignUpActivity : AppCompatActivity() {
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()
    }

    fun init() {
        actionBar = supportActionBar
        actionBar?.hide()
    }
}
