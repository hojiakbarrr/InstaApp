package com.example.instaapp.api

import com.example.instaapp.R
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    val requestInterceptor = Interceptor{chain ->
    val request = chain.request()
        .newBuilder()
        .cacheControl(CacheControl.Builder().maxAge(0,TimeUnit.SECONDS).build())
        .addHeader("X-Parse-Application-Id","qPl8Og2s0KlwwjaC7TXXFRLkpBRXKYnkTswLCCrS")
        .addHeader("X-Parse-Rest-API-Key","uXsIYlcjLJM3Vtv7PzHuU3j6lqt2JH5dMUOFcXsc")
        .addHeader("Content-Type","application/json")
        .build()

        return@Interceptor chain.proceed(request)
    }

    val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client =  OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()

    val builder = Retrofit.Builder()
        .baseUrl("https://parseapi.back4app.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = builder.create(Api::class.java)

}