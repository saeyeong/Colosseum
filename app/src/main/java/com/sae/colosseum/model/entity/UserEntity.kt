package com.sae.colosseum.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val id: String?,
    val email: String?,
    var nickname: String?,
    val createdAt: String?,
    val updatedAt: String?,
    var nick_name: String?,
    val created_at: String?
) : Parcelable