package com.sae.colosseum.network

import android.util.Log
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.RepliesEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.model.entity.TopicInfoEntity
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.utils.GlobalApplication.Companion.loginUser
import com.sae.colosseum.utils.ResultInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ServerClient() {
    private val network = NetworkHelper()

    fun postUser(email: String, password: String, callback: ResultInterface<Boolean>) {
        if (email.isEmpty() || password.isEmpty()) {

        } else {
            network.server.postUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        callback.result(true)
                        GlobalApplication.prefs.myEditText = it.data.token
                        loginUser = it.data.user
                    },
                    onError = {
                        callback.result(false)
                    }
                )
        }
    }


    fun getUserCheck(type: String, value: String, callback: ResultInterface<Boolean>) {
        network.server.getUserCheck( type, value)
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

    fun putUser(email: String, password: String, nickName: String, callback: ResultInterface<Boolean>) {
        network.server.putUser(email, password, nickName)
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

    fun deleteUser(token: String?, text: String?, callback: ResultInterface<ResponseEntity>) {
        network.server.deleteUser(token, text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {
                }
            )
    }

    fun getUserTokenCheck(token: String, loginActivity: () -> Unit, mainActivity: () -> Unit) {
        network.server.getUserTokenCheck(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    loginUser = it.data.user
                    mainActivity()
                },
                onError = {
                    loginActivity()
                }
            )
    }

    fun getTopicList(token: String?, callback: ResultInterface<ResponseEntity>) {
        network.server.getTopicList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {

                }
            )
    }

    fun getTopic(
        token: String?, topicId: String?,
        callback: ResultInterface<DataEntity>
    ) {
        network.server.getTopic(token, topicId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it.data)
                },
                onError = {
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
                    callback.result(false)
                }
            )
    }

    fun postTopicReply(
        token: String?, topicId: Int?, content: String?, parentReplyId: Int?,
        callback: ResultInterface<Boolean>
    ) {
        network.server.postTopicReply(token, topicId, content, parentReplyId)
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

    fun postTopicReplyLike(
        token: String?, replyId: Int?, isLike: Boolean?,
        callback: ResultInterface<RepliesEntity>
    ) {
        network.server.postTopicReplyLike(token, replyId, isLike)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it.data.reply)
                },
                onError = {

                }
            )
    }

    fun deleteTopicReply(
        token: String?, replyId: Int?,
        callback: ResultInterface<ResponseEntity>
    ) {
        network.server.deleteTopicReply(token, replyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {

                }
            )
    }

    fun putTopicReply(
        token: String?, replyId: Int?, content: String?,
        callback: ResultInterface<ResponseEntity>
    ) {
        network.server.putTopicReply(token, replyId, content, null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {
                }
            )
    }

    fun getTopicReReply(
        token: String?, replyId: String?,
        callback: ResultInterface<ResponseEntity>
    ) {
        network.server.getTopicReReply(token, replyId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it)
                },
                onError = {
                }
            )
    }
}