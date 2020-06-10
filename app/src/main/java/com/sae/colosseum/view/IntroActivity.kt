package com.sae.colosseum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.sae.colosseum.R
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.network.ServerClient

class IntroActivity : AppCompatActivity() {

    var handler: Handler? = null
    var runnable: Runnable? = null
    lateinit var serverUtil: ServerClient

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
        serverUtil = ServerClient()

        runnable = Runnable {
            var intentLogin: Intent = Intent(applicationContext, LoginActivity::class.java)
            var intentMain: Intent = Intent(applicationContext, MainActivity::class.java)
            val token = GlobalApplication.prefs.myEditText

            val loginActivity: () -> Unit = {
                startActivity(intentLogin)
                finish()
            }
            val mainActivity: () -> Unit = {
                startActivity(intentMain)
                finish()
            }

            if (token.isNullOrEmpty()) {
                loginActivity()
            } else {
                serverUtil.getUserTokenCheck(token, loginActivity, mainActivity)
            }

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
