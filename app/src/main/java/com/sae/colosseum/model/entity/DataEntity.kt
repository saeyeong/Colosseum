package com.sae.colosseum.model.entity

data class DataEntity (
    val user: UserEntity,
    val token: String,
    val topic: TopicInfoEntity,
    val topics: ArrayList<TopicInfoEntity>,
    val reply: ReplyEntity,
    val replies: ArrayList<ReplyEntity>,
    val my_replies: ArrayList<ReplyEntity>,
    val my_replies_count: Int,
    val unread_noty_count: Int,
    val notifications: ArrayList<NotificationEntity>
)