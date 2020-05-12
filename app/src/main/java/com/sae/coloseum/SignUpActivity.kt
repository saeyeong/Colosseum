package com.sae.coloseum

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sae.coloseum.databinding.ActivitySignUpBinding
import com.sae.coloseum.model.entity.SignUpEntity
import com.sae.coloseum.network.NetworkHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignUpBinding
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        init()
    }

    fun init() {
        actionBar = supportActionBar
        actionBar?.hide()

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
        val name: String = binding.editName.text.toString()
        val phoneNumber: String = binding.editPhoneNumber.text.toString()

        if (password != passwordCheck) {
            Toast.makeText(mContext, "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show()
        } else {
            network.server.postUserInfo(email, password, name, phoneNumber).enqueue(object : Callback<SignUpEntity> {
                override fun onFailure(call: Call<SignUpEntity>, t: Throwable) {
                    Toast.makeText(mContext, t.toString(), Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<SignUpEntity>, response: Response<SignUpEntity>) {
                    if (response.isSuccessful) {
                        Log.d("토큰", response.body()?.data?.token.toString())
                        Toast.makeText(mContext, response.body()?.message.toString(), Toast.LENGTH_LONG).show()
                    } else {
                        val resultJsonStr = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(resultJsonStr,SignUpEntity::class.java)
                        Toast.makeText(mContext,errorResponse.message , Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        signUpApi()
    }
}
