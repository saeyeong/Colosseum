package com.sae.colosseum.model.entity

data class SignUpEntity(
    val email: String?,
    val password: String?,
    val nickname: String?,
    val code: String,
    val message: String,
    val data: DataEntity?
)