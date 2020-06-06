package com.sae.colosseum.network

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.adapter.TopicListAdapter
import com.sae.colosseum.model.entity.TopicEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.ResultInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ServerClient() {
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

    private fun userNicknameCheckApi(email: String, password: String, nickname: String, toast: () -> Unit, startActivity: () -> Unit) {
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

    fun getUserTokenCheck(token: String, loginActivity: () -> Unit, mainActivity: () -> Unit) {
        network.server.getUserTokenCheck(token)
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

    fun getMainPostList(token: String?, context: Context, postList: RecyclerView?) {
        val topicEntity = ArrayList<TopicEntity>()
        var adapter: TopicListAdapter?
        var topicDataEntity: ArrayList<TopicInfoEntity>

        network.server.getMainPostList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    topicEntity.add(it)
                    topicDataEntity = topicEntity[0].data.topics
                    adapter = TopicListAdapter(context, topicDataEntity)

                    postList?.adapter = adapter
                    postList?.layoutManager = LinearLayoutManager(context)
                },
                onError = {
                    Log.d("test111", it.message)
                }
            )
    }

    fun getTopic(
        token: String?, topicId: Int?,
        callback: ResultInterface<TopicEntity>
    ) {
        network.server.getTopic(token, topicId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {
                    Log.d("test", it.message)
                }
            )
    }

    fun postTopicVote(
        token: String?, sideId: Int?,
        callback: ResultInterface<Boolean>
    ) {
        network.server.postTopicVote(token, sideId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(true)
                },
                onError = {
                    Log.d("test", it.message)
                    callback.result(false)
                }
            )
    }

    fun postTopicReply(
        token: String?, topicId: Int?, content: String?,
        callback: ResultInterface<Boolean>
    ) {
        network.server.postTopicReply(token, topicId, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(true)
                },
                onError = {
                    callback.result(false)
                }
            )
    }
}