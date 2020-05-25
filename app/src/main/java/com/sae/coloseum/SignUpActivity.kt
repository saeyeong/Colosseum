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
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
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
            network.server.postUserInfo(email, password, name, phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorToResponse()
                .subscribeBy(
                    onSuccess = {
                        Log.d("TAG","called onSuccess with: it=[$it]")
                        Log.d("토큰", it.data?.token.toString())
                        Toast.makeText(
                            mContext,
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onError = {
                        Log.d("TAG","called onError with: it=[$it]")
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

inline fun <reified T> Single<T>.onErrorToResponse() : Single<T> {
    return flatMap {
        val httpException = it as HttpException
        val errorBody = httpException.response().errorBody()

        if (errorBody == null) {
            Single.error(it)
        } else {
            Single.just(Gson().fromJson(errorBody.string(),T::class.java))
        }
    }
}