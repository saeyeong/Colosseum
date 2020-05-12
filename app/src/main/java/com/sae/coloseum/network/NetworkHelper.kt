package com.sae.coloseum.network

import com.sae.coloseum.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkHelper {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-15-165-177-142.ap-northeast-2.compute.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()
    var server: RetrofitService = retrofit.create(RetrofitService::class.java)

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        }
    }
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .build()
    }
}