package com.sae.colosseum.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ReplyEntity(
    var replies: @RawValue ArrayList<ReplyEntity>?,
    val id: Int?,
    val content: String?,
    val side_id: Int?,
    val user: UserEntity?,
    val updated_at: String?,
    val like_count: Int?,
    val dislike_count: Int?,
    val my_like: Boolean?,
    val my_dislike: Boolean?,
    val reply_count: Int?,
    val project_info: @RawValue TopicInfoEntity
): Parcelable