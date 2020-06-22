package com.sae.colosseum.model.entity

data class DataEntity (
    val user: UserEntity,
    val token: String,
    val topic: TopicInfoEntity,
    val topics: ArrayList<TopicInfoEntity>,
    val replies: ArrayList<RepliesEntity>,
    val reply: RepliesEntity,
    val notifications: ArrayList<NotificationEntity>
)