package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivitySignUpBinding
import com.sae.colosseum.utils.BaseActivity
import com.sae.colosseum.utils.ResultInterface

class SignUpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        init()
    }

    fun init() {
        setListener()
    }

    private fun setListener() {
        binding.btnSignUp.setOnClickListener(this)
    }
    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        val passwordCheck: String = binding.editPasswordCheck.text.toString()
        val nickName: String = binding.editNickName.text.toString()
        val regex = """[a-z|1-9]{4,15}\@[a-z|1-9]{4,15}\..+""".toRegex()
        val intent = Intent(this, LoginActivity::class.java)

        if (email.isEmpty() || password.isEmpty() || nickName.isEmpty()) {

        } else if (!regex.containsMatchIn(input = email)) {
            toast("이메일 양식이 올바르지 않습니다.")
        } else if (password.length !in 8..15) {
            toast("비밀번호는 8자리 이상 15자리 이하로 입력해주세요.")
        } else if (password != passwordCheck) {
            toast("비밀번호가 맞지 않습니다.")
        } else {
            serverClient.getUserCheck("EMAIL", email, object : ResultInterface<Boolean> {
                override fun result(value: Boolean) {
                    if(value) {
                        serverClient.getUserCheck("NICK_NAME", nickName, object : ResultInterface<Boolean> {
                            override fun result(value: Boolean) {
                                if(value) {
                                    serverClient.putUser(email, password, nickName, object : ResultInterface<Boolean> {
                                        override fun result(value: Boolean) {
                                            if(value) {
                                                toast("회원가입이 완료되었습니다.")
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                toast("회원가입 요청에 실패했습니다.")
                                            }
                                        }
                                    })
                                } else {
                                    toast("이미 사용중인 닉네임 입니다.")
                                }
                            }
                        })
                    } else {
                        toast("이미 사용중인 이메일 입니다.")
                    }
                }
            })
        }
    }
}
