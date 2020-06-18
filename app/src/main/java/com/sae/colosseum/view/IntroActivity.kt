package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.sae.colosseum.R
import com.sae.colosseum.utils.BaseActivity

class IntroActivity : BaseActivity() {

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
            var intentLogin: Intent = Intent(this, LoginActivity::class.java)
            var intentMain: Intent = Intent(this, MainActivity::class.java)

            val loginActivity: () -> Unit = {
                startActivity(intentLogin)
                finish()
            }
            val mainActivity: () -> Unit = {
                startActivity(intentMain)
                finish()
            }
            Log.d("test","${token}")
            if (token.isNullOrEmpty()) {
                loginActivity()
            } else {
                serverClient.getUserTokenCheck(token, loginActivity, mainActivity)
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
