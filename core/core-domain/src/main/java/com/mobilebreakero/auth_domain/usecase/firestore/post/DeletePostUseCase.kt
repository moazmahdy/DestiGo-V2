package com.mobilebreakero.auth_domain.usecase.firestore.post

import com.mobilebreakero.auth_domain.repo.PostsRepo
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repo: PostsRepo
) {
    suspend operator fun invoke(id: String) = repo.deletePost(
        id
    )
}
