package com.mobilebreakero.domain.usecase.firestore.post

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.PostsRepo

class AddPostUseCase(
    private val repo: PostsRepo
) {
    suspend operator fun invoke(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) = repo.addPost(post, onSuccessListener, onFailureListener)
}