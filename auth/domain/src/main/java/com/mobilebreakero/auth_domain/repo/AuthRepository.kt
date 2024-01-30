package com.mobilebreakero.auth_domain.repo

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.mobilebreakero.auth_domain.typealiases.*
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {

    val currentUser: FirebaseUser?
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun signInAnonymously(): SignInResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun signUpWithEmailAndPassword(name: String, email: String, password: String): SignUpResponse
    suspend fun signOut(): SignOutResponse
    suspend fun reloadFirebaseUser(): ReloadUserResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    suspend fun resetPassword(password: String): ResetPasswordResponse
    suspend fun sendResetPassword(email: String, confirmationCode: Int): SendResetPasswordResponse
    suspend fun updateEmail(email: String): UpdateEmailResponse
    suspend fun checkUserSignedIn(email: String, password: String, context : Context): CheckUserSignedIn
    suspend fun deleteAccount(): DeleteAccountResponse
}