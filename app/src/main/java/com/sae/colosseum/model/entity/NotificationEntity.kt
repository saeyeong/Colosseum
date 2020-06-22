package com.sae.colosseum.model.entity

data class NotificationEntity (
    val id: Int,
    val receive_user_id: Int,
    val act_user_id: Int,
    val title: String,
    val type: String,
    val message: String,
    val reference_ui: String,
    val focus_object_id: Int,
    val created_at: String,
    val updated_at: String,
    val act_user: UserEntity
)