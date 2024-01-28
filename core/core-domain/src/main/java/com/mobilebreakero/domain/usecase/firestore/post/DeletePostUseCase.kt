package com.mobilebreakero.domain.usecase.firestore.post

import com.mobilebreakero.domain.repo.PostsRepo
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repo: PostsRepo
) {
    suspend operator fun invoke(id: String) = repo.deletePost(
        id
    )
}
