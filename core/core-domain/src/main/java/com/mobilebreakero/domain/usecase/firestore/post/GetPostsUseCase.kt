package com.mobilebreakero.domain.usecase.firestore.post

import com.mobilebreakero.domain.repo.PostsRepo

class GetPostsUseCase(
    private val repo: PostsRepo
) {

    suspend operator fun invoke() = repo.getPosts()
}