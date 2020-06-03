package com.sae.colosseum.model.entity

data class PostListEntity(
    val code: Int,
    val message: String,
    val data: Data
)

data class Data(
    val topic: List<Topic>
)

data class Topic(
    val id: Int,
    val title: String,
    val img_url: String,
    val end_date: String,
    val sides: List<Sides>
)

data class Sides(
    val vote_count: Int
)