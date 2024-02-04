package com.mobilebreakero.auth_domain.usecase.firestore.post

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.repo.PostsRepo

class AddPostUseCase(
    private val repo: PostsRepo
) {
    suspend operator fun invoke(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) = repo.addPost(post, onSuccessListener, onFailureListener)
}