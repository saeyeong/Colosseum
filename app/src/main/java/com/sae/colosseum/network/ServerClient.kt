package com.sae.colosseum.network

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewParent
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sae.colosseum.R
import com.sae.colosseum.adapter.PostListAdapter
import com.sae.colosseum.model.entity.Data
import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.PostListEntity
import com.sae.colosseum.model.entity.Topic
import com.sae.colosseum.utils.GlobalApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_post_list_hour.*

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

        var adapter: PostListAdapter?
        var postListEntity = ArrayList<PostListEntity>()
        var topic: List<Topic>

        network.server.getMainPostList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    postListEntity.add(it)
                    topic = postListEntity[0].data.topic
                    adapter = PostListAdapter(context, topic)

                    postList?.adapter = adapter
                    postList?.layoutManager = LinearLayoutManager(context)
                },
                onError = {
                    Log.d("test", it.message)
                }
            )
    }

}