package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivityLoginBinding
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface

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

        when (v) {
            binding.btnLogin-> {
                serverClient.postUser(email, password, object : ResultInterface<Boolean> {
                    override fun result(value: Boolean) {
                        if(value) {
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "아이디와 비밀번호를 다시 한번 확인해주세요.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }
            binding.btnSignUp -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}