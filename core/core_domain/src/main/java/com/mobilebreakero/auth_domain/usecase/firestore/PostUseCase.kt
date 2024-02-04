package com.mobilebreakero.auth_domain.usecase.firestore

import com.mobilebreakero.auth_domain.usecase.firestore.post.AddCommentUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.AddPostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.DeletePostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostDetails
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostsById
import com.mobilebreakero.auth_domain.usecase.firestore.post.GetPostsUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.LikePostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.post.SharePostUseCase

data class PostUseCase(
    val addPost: AddPostUseCase,
    val getPosts: GetPostsUseCase,
    val likePost: LikePostUseCase,
    val getPostsByUserId: GetPostsById,
    val deletePost: DeletePostUseCase,
    val sharePost: SharePostUseCase,
    val addComment: AddCommentUseCase,
    val getPostDetails: GetPostDetails
)
