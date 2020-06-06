package com.sae.colosseum.model.entity

data class TopicInfoEntity(
    val id: Int,
    val title: String,
    val img_url: String,
    val start_date: String,
    val end_date: String,
    val sides: List<SidesEntity>,
    val reply_count: Int,
    val my_side_id: Int,
    val replies: ArrayList<RepliesEntity>
)