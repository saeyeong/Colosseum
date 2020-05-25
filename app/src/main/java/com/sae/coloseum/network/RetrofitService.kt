package com.sae.coloseum.network

import com.sae.coloseum.model.entity.SignUpEntity
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT

interface RetrofitService {
    @PUT("user")
    @FormUrlEncoded
    fun postUserInfo(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("nick_name") nickname: String?,
        @Field("phone_num") phoneNumber: String?
    ): Single<SignUpEntity>
}