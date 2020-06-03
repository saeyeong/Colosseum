package com.sae.colosseum.model

import com.sae.colosseum.R
import com.sae.colosseum.model.entity.AlarmListEntity
import com.sae.colosseum.model.entity.CommentListEntity
import com.sae.colosseum.model.entity.PostListEntity
import com.sae.colosseum.network.ServerClient
import com.sae.colosseum.utils.GlobalApplication

class DataModel {

    var itemsList2: ArrayList<CommentListEntity>? = null
        get() = field ?: ArrayList()

    var itemsList3: ArrayList<AlarmListEntity>? = null
        get() = field ?: ArrayList()

    init {
        itemsList2 = makeTestItems2()
        itemsList3 = makeTestItems3()
    }

    fun makeTestItems2(): ArrayList<CommentListEntity> {
        var items = ArrayList<CommentListEntity>()
        for (i in 0..10) {
            var item = CommentListEntity(
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

    fun makeTestItems3(): ArrayList<AlarmListEntity> {
        var items = ArrayList<AlarmListEntity>()
        for (i in 0..10) {
            var item = AlarmListEntity(
                R.drawable.heart_color,
                "새요미",
                R.string.heart_like_alarm
            )
            items.add(item)
        }

        return items
    }

}
