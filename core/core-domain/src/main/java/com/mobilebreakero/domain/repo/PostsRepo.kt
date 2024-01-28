package com.mobilebreakero.domain.repo

import android.content.Context
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response


typealias postResponse = Response<List<Post>>
typealias postDetailsResponse = Response<Post>?
typealias addPostResponse = Response<Boolean>
typealias updatePostResponse = Response<Boolean>

interface PostsRepo {

    suspend fun addPost(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addPostResponse

    suspend fun getPosts(): postResponse

    suspend fun likePost(
        postId: String,
        like: Int,
        userId: String,
        context: Context
    ): updatePostResponse

    suspend fun getPostsByUserId(userId: String): postResponse

    suspend fun deletePost(postId: String): updatePostResponse

    suspend fun sharePost(postId: String, userId: String, userName: String): addPostResponse

    suspend fun addComment(
        postId: String,
        comment: String,
        userId: String,
        userName: String
    ): updatePostResponse

    suspend fun getPostDetails(postId: String): postDetailsResponse
}