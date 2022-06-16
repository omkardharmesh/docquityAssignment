package com.omkar.docquityassignment.repository

import com.omkar.docquityassignment.api.RetrofitInstance

class Repository {

    suspend fun getPost(postId: Int) =
        RetrofitInstance.api.getPost(postId)
}