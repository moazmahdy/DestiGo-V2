package com.mobilebreakero.domain.usecase.firestore.post

import com.mobilebreakero.domain.repo.PostsRepo
import javax.inject.Inject

class SharePostUseCase @Inject constructor(
    private val repo: PostsRepo
) {

    suspend operator fun invoke(userId: String, postId: String, userName: String) = repo.sharePost(
        userId = userId, postId = postId, userName = userName
    )
}
