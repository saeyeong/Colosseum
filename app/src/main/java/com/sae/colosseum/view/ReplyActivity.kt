package com.sae.colosseum.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityReplyBinding

class ReplyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReplyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply)

        init()
    }

    fun init() {

    }
}
