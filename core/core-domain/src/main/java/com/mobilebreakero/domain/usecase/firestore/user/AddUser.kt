package com.mobilebreakero.domain.usecase.firestore.user

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepository

class AddUser(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener) =
        repo.addUser(user, onSuccessListener, onFailureListener)
}