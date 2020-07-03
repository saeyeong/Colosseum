package com.sae.colosseum.model.entity

data class TopicInfoEntity(
    var id: Int?,
    val title: String?,
    val img_url: String?,
    val start_date: String?,
    val end_date: String?,
    val sides: List<SidesEntity>?,
    val reply_count: Int?,
    var my_side_id: Int?,
    var replies: ArrayList<ReplyEntity>?,
    var is_my_like_topic: Boolean = false
)