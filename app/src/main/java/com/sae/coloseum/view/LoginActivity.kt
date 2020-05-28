package com.sae.coloseum.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sae.coloseum.R
import com.sae.coloseum.databinding.ActivityLoginBinding
import com.sae.coloseum.network.NetworkHelper
import com.sae.coloseum.utils.GlobalApplication.Companion.prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        init()
    }

    fun init() {
        setListener()
    }

    fun setListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)

    }

    fun loginApi() {
        val mContext: Context = this;
        val network = NetworkHelper()
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        var token: String
        val intent = Intent(applicationContext, MainActivity::class.java)

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {

        } else {
            network.server.postUserInfo(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        token = it.data?.token.toString()
                        prefs.myEditText = token
                        startActivity(intent)
                        finish()
                    },
                    onError = {
                        Toast.makeText(
                            mContext,
                            "아이디와 비밀번호를 다시 한번 확인해주세요.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin-> {
                loginApi()
            }
            binding.btnSignUp -> {
                val intent = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}