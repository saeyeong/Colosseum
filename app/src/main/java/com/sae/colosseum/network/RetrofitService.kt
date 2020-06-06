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
    ): Single<SignUpEntity>

    @POST("user")
    @FormUrlEncoded
    fun postUserInfo(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Single<SignUpEntity>

    @GET("user_check")
    fun getUserIDCheck(
        @Query("type") type: String?,
        @Query("value") value: String?
    ): Single<EmailCheckEntity>

    @GET("user_info")
    fun getUserTokenCheck(
        @Header("X-Http-Token") token: String?
    ): Single<DataEntity>

    @GET("v2/main_info")
    fun getMainPostList(
        @Header("X-Http-Token") token: String?
    ): Single<TopicEntity>

    @GET("topic/{topicId}")
    fun getTopic(
        @Header("X-Http-Token") token: String?,
        @Path("topicId") topicId: String?
    ): Single<TopicEntity>

    @POST("topic_vote")
    @FormUrlEncoded
    fun postTopicVote(
        @Header("X-Http-Token") token: String?,
        @Field("side_id") sideId: Int?
    ): Single<TopicEntity>

    @POST("topic_reply")
    @FormUrlEncoded
    fun postTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("topic_id") topicId: Int?,
        @Field("content") content: String?
    ): Single<TopicEntity>
}