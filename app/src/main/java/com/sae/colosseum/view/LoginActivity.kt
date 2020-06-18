package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityLoginBinding
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.BaseActivity

class LoginActivity : BaseActivity(), View.OnClickListener {

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
                this,
                "아이디와 비밀번호를 다시 한번 확인해주세요.",
                Toast.LENGTH_LONG
            ).show()
        }

        when (v) {
            binding.btnLogin-> {
                serverClient.postUser(email, password, startActivity, toast)
            }
            binding.btnSignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}