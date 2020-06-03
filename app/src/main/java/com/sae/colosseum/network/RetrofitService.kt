package com.sae.colosseum.network

import com.sae.colosseum.model.entity.DataEntity
import com.sae.colosseum.model.entity.EmailCheckEntity
import com.sae.colosseum.model.entity.PostListEntity
import com.sae.colosseum.model.entity.SignUpEntity
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
    ): Single<PostListEntity>
}