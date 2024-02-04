package com.mobilebreakero.auth_domain.usecase.firestore.post

import android.content.Context
import com.mobilebreakero.auth_domain.repo.PostsRepo
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val repo: PostsRepo
) {
    suspend operator fun invoke(
        postId: String, like: Int, userId: String,
        context: Context
    ) = repo.likePost(postId, like, userId, context)
}