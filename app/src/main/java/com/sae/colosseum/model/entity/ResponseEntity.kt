package com.sae.colosseum.model.entity

data class ResponseEntity(
    val token: String,
    val code: Int,
    val message: String,
    val data: DataEntity
)