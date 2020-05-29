package com.sae.coloseum.model.entity

data class SignUpEntity(
    val email: String?,
    val password: String?,
    val nickname: String?,
    val code: String,
    val message: String,
    val data: DataEntity?
)

data class DataEntity(
    val user: UserEntity,
    val token: String
)

data class UserEntity(
    val id: String,
    val email: String,
    val nickname: String,
    val createdAt: String,
    val updatedAt: String
)
