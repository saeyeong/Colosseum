package com.sae.coloseum.model.entity

data class PostListEntity(
    val postNumber: String,
    val postTitle: String,
    val agreeCount: String,
    val disagreeCount: String,
    val commentCount: String,
    val nickName: String
)