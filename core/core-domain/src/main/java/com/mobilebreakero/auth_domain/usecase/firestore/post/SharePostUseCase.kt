package com.mobilebreakero.auth_domain.usecase.firestore.post

import com.mobilebreakero.auth_domain.repo.PostsRepo
import javax.inject.Inject

class SharePostUseCase @Inject constructor(
    private val repo: PostsRepo
) {

    suspend operator fun invoke(userId: String, postId: String, userName: String) = repo.sharePost(
        userId = userId, postId = postId, userName = userName
    )
}
