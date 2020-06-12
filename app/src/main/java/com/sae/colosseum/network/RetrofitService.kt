package com.sae.colosseum.network

import com.sae.colosseum.model.entity.*
import io.reactivex.Single
import retrofit2.http.*

interface RetrofitService {
    @PUT("user")
    @FormUrlEncoded
    fun putUserInfo(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("nick_name") nickname: String?
    ): Single<ResponseEntity>

    @POST("user")
    @FormUrlEncoded
    fun postUserInfo(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Single<ResponseEntity>

    @GET("user_check")
    fun getUserIDCheck(
        @Query("type") type: String?,
        @Query("value") value: String?
    ): Single<ResponseEntity>

    @GET("user_info")
    fun getUserTokenCheck(
        @Header("X-Http-Token") token: String?
    ): Single<ResponseEntity>

    @GET("v2/main_info")
    fun getTopicList(
        @Header("X-Http-Token") token: String?
    ): Single<ResponseEntity>

    @GET("topic/{topicId}")
    fun getTopic(
        @Header("X-Http-Token") token: String?,
        @Path("topicId") topicId: String?
    ): Single<ResponseEntity>

    @POST("topic_vote")
    @FormUrlEncoded
    fun postTopicVote(
        @Header("X-Http-Token") token: String?,
        @Field("side_id") sideId: Int?
    ): Single<ResponseEntity>

    @POST("topic_reply")
    @FormUrlEncoded
    fun postTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("topic_id") topicId: Int?,
        @Field("content") content: String?
    ): Single<ResponseEntity>

    @POST("topic_reply_like")
    @FormUrlEncoded
    fun postTopicReplyLike(
        @Header("X-Http-Token") token: String?,
        @Field("reply_id") replyId: Int?,
        @Field("is_like") isLike: Boolean?
    ): Single<ResponseEntity>

    @DELETE("topic_reply")
    fun deleteTopicReply(
        @Header("X-Http-Token") token: String?,
        @Query("reply_id") replyId: Int?
    ): Single<ResponseEntity>

    @PUT("topic_reply")
    @FormUrlEncoded
    fun putTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("reply_id") replyId: Int?,
        @Field("content") content: String?
    ): Single<ResponseEntity>

    @GET("topic_reply/{replyId}")
    fun getTopicReReply(
        @Header("X-Http-Token") token: String?,
        @Path("replyId") replyId: String?
    ): Single<ResponseEntity>
}