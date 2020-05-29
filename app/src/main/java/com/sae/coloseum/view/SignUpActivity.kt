package com.sae.coloseum.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.sae.coloseum.R
import com.sae.coloseum.databinding.ActivitySignUpBinding
import com.sae.coloseum.model.DataModel
class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var dataModel: DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        init()
    }

    fun init() {
        setListener()
        dataModel = DataModel()
    }

    fun setListener() {
        binding.btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val mContext: Context = this
        val email: String = binding.editEmail.text.toString()
        val password: String = binding.editPassword.text.toString()
        val passwordCheck: String = binding.editPasswordCheck.text.toString()
        val nickname: String = binding.editNickname.text.toString()
        val intent = Intent(mContext, LoginActivity::class.java)
        val startActivity: () -> Unit = {
            startActivity(intent)
            finish()
        }

        dataModel.signUpApi(mContext, email, password, passwordCheck, nickname, startActivity)
    }
}