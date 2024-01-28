package com.mobilebreakero.domain.usecase.firestore.post

import com.mobilebreakero.domain.repo.PostsRepo
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val repo: PostsRepo
) {

    suspend operator fun invoke(id: String, comment: String, userId: String, userName: String) =
        repo.addComment(
            userId = userId, comment = comment, postId = id, userName = userName
        )

}
