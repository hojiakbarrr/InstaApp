package com.example.instaapp.api

import com.example.instaapp.model.AddPostRequest
import com.example.instaapp.model.AddPostResponse
import com.example.instaapp.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("Classes/Posts")
    fun getAllPosts(): Call<PostResponse>

    @POST("Classes/Posts")
    fun createPost(@Body post: AddPostRequest): Call<AddPostResponse>

}