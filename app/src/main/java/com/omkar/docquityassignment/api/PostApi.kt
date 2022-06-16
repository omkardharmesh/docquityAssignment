package com.omkar.docquityassignment.api

import com.omkar.docquityassignment.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("posts/{id}")
    suspend fun getPost(
        @Path("id") id: Int,
    ): Response<Post>
}