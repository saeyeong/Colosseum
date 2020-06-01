package com.sae.coloseum.utils

import android.content.Context
import android.widget.Toast
import com.sae.coloseum.network.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ServerUtil(private val mContext: Context) {
    private val network = NetworkHelper()


    fun loginApi(email: String, password: String, startActivity: () -> Unit, toast: () -> Unit) {
        var token: String

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {

        } else {
            network.server.postUserInfo(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        token = it.data?.token.toString()
                        GlobalApplication.prefs.myEditText = token
                        startActivity()
                    },
                    onError = {
                        toast()
                    }
                )
        }
    }


    fun signUpApi(email: String, password: String, nickname: String, toast: () -> Unit, startActivity: () -> Unit) {
        network.server.getUserIDCheck( "EMAIL", email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    userNicknameCheckApi(email, password, nickname, toast, startActivity)
                },
                onError = {
                    toast()
                }
            )
    }

    fun userNicknameCheckApi(email: String, password: String, nickname: String, toast: () -> Unit, startActivity: () -> Unit) {
        network.server.getUserIDCheck( "NICKNAME", nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    putUserInfo(email, password, nickname, toast, startActivity)
                },
                onError = {
                    toast()
                }
            )
    }

    private fun putUserInfo(email: String, password: String, nickname: String, toast: () -> Unit, startActivity: () -> Unit) {
        network.server.putUserInfo(email, password, nickname)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    toast()
                    startActivity()
                },
                onError = {
                    toast()
                }
            )
    }

    fun getMainInfo(token: String, loginActivity: () -> Unit, mainActivity: () -> Unit) {
        network.server.getMainInfo(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mainActivity()
                },
                onError = {
                    loginActivity()

                }
            )
    }

}