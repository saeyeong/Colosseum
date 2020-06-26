package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.sae.colosseum.R
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.interfaces.ResultInterface
import com.sae.colosseum.model.entity.ResponseEntity

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

            if (token.isNullOrEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                serverClient.getUserInfo(token, object :
                    ResultInterface<ResponseEntity, Boolean> {
                    override fun result(value: ResponseEntity?, boolean: Boolean) {
                        if(boolean) {
                            val intent = Intent(this@IntroActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@IntroActivity, "환영합니다", Toast.LENGTH_LONG).show()
                        } else {
                            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                })
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
