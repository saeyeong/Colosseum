package com.sae.colosseum.model.entity

data class TopicDataEntity (
    val topic: TopicInfoEntity,
    val topics: ArrayList<TopicInfoEntity>,
    val replies: List<RepliesEntity>
)