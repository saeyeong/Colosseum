package com.sae.colosseum.network

import android.util.Log
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.ReplyEntity
import com.sae.colosseum.model.entity.ResponseEntity
import com.sae.colosseum.utils.GlobalApplication
import com.sae.colosseum.interfaces.ResultInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ServerClient() {
    private val network = NetworkHelper()




    fun postUser(email: String, password: String, startMainActivity: ()->Unit, showToast: (m:String)->Unit) {
        network.server.postUser(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    GlobalApplication.prefs.userToken = it.data.token
                    GlobalApplication.prefs.userId = it.data.user.id
                    GlobalApplication.prefs.userNickName = it.data.user.nick_name
                    GlobalApplication.prefs.userEmail = it.data.user.email
                    startMainActivity.invoke()
                },
                onError = {
                    showToast.invoke("아이디와 비밀번호를 다시 한번 확인해주세요.")
                }
            )
    }


    fun getUserCheck(type: String, value: String, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.getUserCheck( type, value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun putUser(email: String, password: String, nickName: String, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.putUser(email, password, nickName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun patchUser(token: String?, nickName: String?, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.patchUser(token, nickName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun patchUser(token: String?, currentPassword: String?, newPassword: String?, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.patchUser(token, currentPassword, newPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun deleteUser(token: String?, text: String?, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.deleteUser(token, text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getUserInfo(token: String?, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.getUserInfo(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    GlobalApplication.prefs.userId = it.data.user.id
                    GlobalApplication.prefs.userNickName = it.data.user.nick_name
                    GlobalApplication.prefs.userEmail = it.data.user.email
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getUserInfo(token: String?, needReplies: Int, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.getUserInfo(token, needReplies)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getTopicList(token: String?, callback: ResultInterface<ResponseEntity, Boolean>) {
        network.server.getTopicList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getTopic(
        token: String?, topicId: String?,
        callback: ResultInterface<DataEntity, Boolean>
    ) {
        network.server.getTopic(token, topicId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it.data, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getTopic(
        token: String?, topicId: String?, orderType: String, pageNum: Int,
        callback: ResultInterface<DataEntity, Boolean>
    ) {
        network.server.getTopic(token, topicId.toString(), orderType, pageNum.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it.data, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun postTopicVote(
        token: String?, sideId: Int?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.postTopicVote(token, sideId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun postTopicReply(
        token: String?, content: String?, parentReplyId: Int?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.postTopicReply(token, content, parentReplyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun postTopicReply(
        token: String?, topicId: Int?, content: String?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.postTopicReply(token, topicId, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                    Log.d("test","성공")
                },
                onError = {
                    callback.result(null, false)
                    Log.d("test",it.message ?: "")
                }
            )
    }

    fun postTopicLike(
        token: String?, topicId: Int?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.postTopicLike(token, topicId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getTopicLike(
        token: String?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.getTopicLike(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun postTopicReplyLike(
        token: String?, replyId: Int?, isLike: Boolean?,
        callback: ResultInterface<ReplyEntity, Boolean>
    ) {
        network.server.postTopicReplyLike(token, replyId, isLike)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it.data.reply, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun deleteTopicReply(
        token: String?, replyId: Int?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.deleteTopicReply(token, replyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun putTopicReply(
        token: String?, replyId: Int?, content: String?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.putTopicReply(token, replyId, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getTopicReply(
        token: String?, replyId: String?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.getTopicReply(token, replyId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun getNotification(
        token: String?,
        needAllNotis: Boolean?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.getNotification(token, needAllNotis)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }

    fun postNotification(
        token: String?,
        notiId: Int?,
        callback: ResultInterface<ResponseEntity, Boolean>
    ) {
        network.server.postNotification(token, notiId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.result(it, true)
                },
                onError = {
                    callback.result(null, false)
                }
            )
    }
}