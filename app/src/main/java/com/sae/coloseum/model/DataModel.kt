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





}
