package com.sae.coloseum.model

import android.content.Context
import android.widget.Toast
import com.sae.coloseum.model.entity.CmtListEntity
import com.sae.coloseum.model.entity.PostListEntity
import com.sae.coloseum.network.NetworkHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DataModel {
    private val network = NetworkHelper()
    var itemsList: ArrayList<PostListEntity>? = null
        get() = field ?: ArrayList()

    var itemsList2: ArrayList<CmtListEntity>? = null
        get() = field ?: ArrayList()

    init {
        itemsList = makeTestItems()
        itemsList2 = makeTestItems2()
    }

    fun makeTestItems(): ArrayList<PostListEntity> {
        var items = ArrayList<PostListEntity>()
        for (i in 0..10) {
            var item = PostListEntity(
                "${i}",
                "제목",
                "99999",
                "99999",
                "99999",
                "작성자"
            )
            items.add(item)
        }

        return items
    }

    fun makeTestItems2(): ArrayList<CmtListEntity> {
        var items = ArrayList<CmtListEntity>()
        for (i in 0..10) {
            var item = CmtListEntity(
                "@mipmap/ic_launcher",
                "새요미",
                "2020-03-02 22:01",
                "33",
                "222",
                "안녕하세요~~~~~"
            )
            items.add(item)
        }

        return items
    }




    fun signUpApi(mContext: Context, email: String, password: String, passwordCheck: String, nickname: String, startActivity: () -> Unit) {
        val regex = """[a-z|1-9]{4,15}\@[a-z|1-9]{4,15}\..+""".toRegex()

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || nickname.isNullOrEmpty()) {

        } else if (!regex.containsMatchIn(input = email)) {
            Toast.makeText(mContext, "이메일 양식이 올바르지 않습니다.", Toast.LENGTH_LONG).show()
        } else if (password.length !in 8..15) {
            Toast.makeText(mContext, "비밀번호는 8자리 이상 15자리 이하로 입력해주세요.", Toast.LENGTH_LONG).show()
        } else if (password != passwordCheck) {
            Toast.makeText(mContext, "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show()
        } else {
            network.server.getUserIDCheck("EMAIL", email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        putUserInfo(mContext, email, password, nickname, startActivity)
                    },
                    onError = {
                        Toast.makeText(
                            mContext,
                            "중복된 이메일 입니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        }
    }

    private fun putUserInfo(mContext: Context, email: String, password: String, nickname: String, startActivity: () -> Unit) {
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

                    startActivity()

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
