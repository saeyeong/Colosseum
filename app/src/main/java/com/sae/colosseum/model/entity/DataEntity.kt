package com.sae.colosseum.model.entity

data class DataEntity(
    val user: UserEntity,
    val token: String,
    val message: String
)