package com.sae.colosseum.model.entity

data class CommentListEntity(
    val authorThumbnail: String,
    val cmtNickname: String,
    val txtDateCmt: String,
    val numLike: String,
    val numDislike: String,
    val txtCmt: String
)