package com.sae.coloseum.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sae.coloseum.R
import com.sae.coloseum.utils.GlobalApplication
import com.sae.coloseum.utils.ServerUtil

class IntroActivity : AppCompatActivity() {

    var handler: Handler? = null
    var runnable: Runnable? = null
    lateinit var serverUtil: ServerUtil

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

        serverUtil = ServerUtil(this)

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
                serverUtil.getMainInfo(token, loginActivity, mainActivity)
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
