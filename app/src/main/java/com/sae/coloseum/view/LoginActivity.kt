package com.sae.coloseum.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sae.coloseum.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}