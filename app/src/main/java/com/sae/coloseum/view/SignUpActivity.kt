package com.sae.coloseum.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sae.coloseum.R
import com.sae.coloseum.databinding.ActivitySignUpBinding
import com.sae.coloseum.network.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        init()
    }

    fun init() {
        setListener()
    }

    fun setListener() {
        binding.btnSignUp.setOnClickListener(this)
    }

    fun signUpApi() {
        val mContext: Context = this;
        val network = NetworkHelper()
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        val passwordCheck: String = binding.editPasswordCheck.text.toString()
        val nickname: String = binding.editNickname.text.toString()
        val regex = """[a-z|1-9]{4,15}\@[a-z|1-9]{4,15}\..+""".toRegex()

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || nickname.isNullOrEmpty()) {

        }
        else if (!regex.containsMatchIn(input = email)) {
            Toast.makeText(mContext, "이메일 양식이 올바르지 않습니다.", Toast.LENGTH_LONG).show()
        }
        else if (password.length !in 8 .. 15) {
            Toast.makeText(mContext, "비밀번호는 8자리 이상 15자리 이하로 입력해주세요.", Toast.LENGTH_LONG).show()
        }
        else if (password!=passwordCheck) {
            Toast.makeText(mContext, "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show()
        }
        else {
            network.server.putUserInfo(email, password, nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        Toast.makeText(
                            mContext,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(mContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    onError = {
                        Toast.makeText(
                            mContext,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        }
    }

    override fun onClick(v: View?) {
        signUpApi()
    }
}