package com.sae.colosseum.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sae.colosseum.R
import com.sae.colosseum.databinding.ActivitySignUpBinding
import com.sae.colosseum.utils.BaseActivity

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

    override fun onClick(v: View?) {
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        val passwordCheck: String = binding.editPasswordCheck.text.toString()
        val nickName: String = binding.editNickName.text.toString()

        val regex = """[a-z|1-9]{4,15}\@[a-z|1-9]{4,15}\..+""".toRegex()

        val intent = Intent(this, LoginActivity::class.java)
        val startActivity: () -> Unit = {
            startActivity(intent)
            finish()
        }

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || nickName.isNullOrEmpty()) {

        } else if (!regex.containsMatchIn(input = email)) {
            Toast.makeText(this, "이메일 양식이 올바르지 않습니다.", Toast.LENGTH_LONG).show()
        } else if (password.length !in 8..15) {
            Toast.makeText(this, "비밀번호는 8자리 이상 15자리 이하로 입력해주세요.", Toast.LENGTH_LONG).show()
        } else if (password != passwordCheck) {
            Toast.makeText(this, "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show()
        } else {
            val toast: () -> Unit = {Toast.makeText(this, "이메일 또는 닉네임이 중복입니다.", Toast.LENGTH_LONG).show()}

            serverClient.signUpApi(email, password, nickName, toast, startActivity)

        }

    }
}
