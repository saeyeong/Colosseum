package com.sae.coloseum.model

import com.sae.coloseum.model.entity.PostListEntity

class DataModel {
    var itemsList: ArrayList<PostListEntity>? = null
        get() = field ?: ArrayList()

    init {
        itemsList = makeTestItems()
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
}
