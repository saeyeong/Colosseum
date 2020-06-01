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
import com.sae.coloseum.utils.ServerUtil

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mContext: Context
    lateinit var serverUtil: ServerUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        init()
    }

    fun init() {
        setListener()
        mContext = this
        serverUtil = ServerUtil(mContext)
    }

    fun setListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)

    }



    override fun onClick(v: View?) {
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        val intent = Intent(this, MainActivity::class.java)
        val startActivity: () -> Unit = {
            startActivity(intent)
            finish()
        }
        val toast: () -> Unit = {
            Toast.makeText(
                mContext,
                "아이디와 비밀번호를 다시 한번 확인해주세요.",
                Toast.LENGTH_LONG
            ).show()
        }

        when (v) {
            binding.btnLogin-> {
                serverUtil.loginApi(email, password, startActivity, toast)
            }
            binding.btnSignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}