package com.mobilebreakero.auth_domain.usecase.firestore.post

import com.mobilebreakero.auth_domain.repo.PostsRepo

class GetPostsUseCase(
    private val repo: PostsRepo
) {

    suspend operator fun invoke() = repo.getPosts()
}