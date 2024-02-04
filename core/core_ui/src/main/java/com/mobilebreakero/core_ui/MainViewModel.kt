package com.mobilebreakero.core_ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth_domain.repo.userResponse
import com.mobilebreakero.auth_domain.usecase.firestore.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val firebaseUser = Firebase.auth.currentUser

    init {
        getUser(firebaseUser?.uid ?: "")
    }

    fun getUser(id: String): Flow<userResponse> {
        return flow {
            val result = useCase.getUserByID(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}