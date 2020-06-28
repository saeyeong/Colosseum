package com.sae.colosseum.network

import com.sae.colosseum.model.entity.*
import io.reactivex.Single
import retrofit2.http.*

interface RetrofitService {

    @GET("user_check")
    fun getUserCheck(
        @Query("type") type: String?,
        @Query("value") value: String?
    ): Single<ResponseEntity>

    @GET("user_info")
    fun getUserInfo(
        @Header("X-Http-Token") token: String?
    ): Single<ResponseEntity>

    @GET("user_info")
    fun getUserInfo(
        @Header("X-Http-Token") token: String?,
        @Query("need_replies") needReplies: Int?
    ): Single<ResponseEntity>




    @PUT("user")
    @FormUrlEncoded
    fun putUser(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("nick_name") nickName: String?
    ): Single<ResponseEntity>

    @POST("user")
    @FormUrlEncoded
    fun postUser(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Single<ResponseEntity>

    @PATCH("user")
    @FormUrlEncoded
    fun patchUser(
        @Header("X-Http-Token") token: String?,
        @Field("nick_name") nickName: String?
    ): Single<ResponseEntity>

    @PATCH("user")
    @FormUrlEncoded
    fun patchUser(
        @Header("X-Http-Token") token: String?,
        @Field("current_password") currentPassword: String?,
        @Field("new_password") newPassword: String?
    ): Single<ResponseEntity>

    @DELETE("user")
    fun deleteUser(
        @Header("X-Http-Token") token: String?,
        @Query("text") text: String?
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

    @GET("topic/{topicId}")
    fun getTopic(
        @Header("X-Http-Token") token: String?,
        @Path("topicId") topicId: String?,
        @Query("order_type") orderType: String?,
        @Query("page_num") pageNum: String?
    ): Single<ResponseEntity>

    @POST("topic_vote")
    @FormUrlEncoded
    fun postTopicVote(
        @Header("X-Http-Token") token: String?,
        @Field("side_id") sideId: Int?
    ): Single<ResponseEntity>



    @PUT("topic_reply")
    @FormUrlEncoded
    fun putTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("reply_id") replyId: Int?,
        @Field("content") content: String?
    ): Single<ResponseEntity>

    @PUT("topic_reply")
    @FormUrlEncoded
    fun putTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("reply_id") replyId: Int?,
        @Field("content") content: String?,
        @Field("parent_reply_id") parentReplyId: Int?
    ): Single<ResponseEntity>

    @POST("topic_reply")
    @FormUrlEncoded
    fun postTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("topic_id") topicId: Int?,
        @Field("content") content: String?
    ): Single<ResponseEntity>

    @POST("topic_reply")
    @FormUrlEncoded
    fun postTopicReply(
        @Header("X-Http-Token") token: String?,
        @Field("content") content: String?,
        @Field("parent_reply_id") parentReplyId: Int?
    ): Single<ResponseEntity>

    @DELETE("topic_reply")
    fun deleteTopicReply(
        @Header("X-Http-Token") token: String?,
        @Query("reply_id") replyId: Int?
    ): Single<ResponseEntity>

    @GET("topic_reply/{replyId}")
    fun getTopicReply(
        @Header("X-Http-Token") token: String?,
        @Path("replyId") replyId: String?
    ): Single<ResponseEntity>

    @POST("topic_like")
    @FormUrlEncoded
    fun postTopicLike(
        @Header("X-Http-Token") token: String?,
        @Field("topic_id") topicId: Int?
    ): Single<ResponseEntity>

    @GET("topic_like")
    fun getTopicLike(
        @Header("X-Http-Token") token: String?
    ): Single<ResponseEntity>



    @POST("topic_reply_like")
    @FormUrlEncoded
    fun postTopicReplyLike(
        @Header("X-Http-Token") token: String?,
        @Field("reply_id") replyId: Int?,
        @Field("is_like") isLike: Boolean?
    ): Single<ResponseEntity>


    @GET("notification")
    fun getNotification(
        @Header("X-Http-Token") token: String?
    ): Single<ResponseEntity>
}