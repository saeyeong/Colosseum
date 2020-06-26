package com.sae.colosseum.model.entity

data class TopicInfoEntity(
    var id: Int = -1,
    val title: String = "",
    val img_url: String = "",
    val start_date: String = "",
    val end_date: String = "",
    val sides: List<SidesEntity> = listOf(SidesEntity(0, 0),SidesEntity(0, 0)),
    val reply_count: Int = -1,
    var my_side_id: Int = -1,
    var replies: ArrayList<ReplyEntity>? = null,
    var is_my_like_topic: Boolean = false
)