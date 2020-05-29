package com.sae.coloseum.network

import com.sae.coloseum.model.entity.EmailCheckEntity
import com.sae.coloseum.model.entity.SignUpEntity
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
}