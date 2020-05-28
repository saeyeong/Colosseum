package com.sae.coloseum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBar
import com.sae.coloseum.MainActivity
import com.sae.coloseum.R
import com.sae.coloseum.utils.GlobalApplication

class IntroActivity : AppCompatActivity() {

    var handler: Handler? = null
    var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        init()
    }

    override fun onResume() {
        super.onResume()

        delayApi()
    }

    override fun onPause() {
        super.onPause()

        handler?.removeCallbacks(runnable)
    }

    fun init() {


        runnable = Runnable {
            var intent: Intent
            val token = GlobalApplication.prefs.myEditText

            intent =
                if (token.isNullOrEmpty()) {
                    Intent(applicationContext, LoginActivity::class.java)
                } else {
                    Intent(applicationContext, MainActivity::class.java)
                }
            startActivity(intent)
            finish()
        }

        handler = Handler()

    }

    fun delayApi() {
        handler?.run {
            postDelayed(runnable, 1300)
        }
    }
}
