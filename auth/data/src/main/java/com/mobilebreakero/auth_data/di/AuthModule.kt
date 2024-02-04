package com.mobilebreakero.auth_data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth_data.repository.AuthRepositoryImpl
import com.mobilebreakero.auth_domain.repo.AuthRepository
import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import com.mobilebreakero.auth_domain.usecase.AuthUseCase
import com.mobilebreakero.auth_domain.usecase.CheckUserSignedInUseCase
import com.mobilebreakero.auth_domain.usecase.CurrentUser
import com.mobilebreakero.auth_domain.usecase.DeleteAccount
import com.mobilebreakero.auth_domain.usecase.GetAuthState
import com.mobilebreakero.auth_domain.usecase.ReloadUser
import com.mobilebreakero.auth_domain.usecase.RestPassword
import com.mobilebreakero.auth_domain.usecase.SendEmailVerification
import com.mobilebreakero.auth_domain.usecase.SendPasswordResetEmail
import com.mobilebreakero.auth_domain.usecase.SignInAnnonymously
import com.mobilebreakero.auth_domain.usecase.SignInWithEmailAndPassword
import com.mobilebreakero.auth_domain.usecase.SignOut
import com.mobilebreakero.auth_domain.usecase.SignUpWithEmailAndPassword
import com.mobilebreakero.auth_domain.usecase.UpdateEmail
import com.mobilebreakero.auth_domain.usecase.UpdatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthUseCases(
        repo: AuthRepository
    ) = AuthUseCase(
        getAuthState = GetAuthState(repo),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(repo),
        signUpWithEmailAndPassword = SignUpWithEmailAndPassword(repo),
        signOut = SignOut(repo),
        signInAnonymously = SignInAnnonymously(repo),
        sendEmailVerification = SendEmailVerification(repo),
        sendPasswordResetEmail = SendPasswordResetEmail(repo),
        currentUser = CurrentUser(repo),
        reloadUser = ReloadUser(repo),
        resetPassword = RestPassword(repo),
        updatePassword = UpdatePassword(repo),
        updateEmail = UpdateEmail(repo),
        checkUserSignedIn = CheckUserSignedInUseCase(repo),
        deleteAccount = DeleteAccount(repo)
    )

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun providesAuthRepository(
        auth: FirebaseAuth,
        repository: FireStoreRepository,
        fireStore: FirebaseFirestore
    ): AuthRepository =
        AuthRepositoryImpl(auth, repository, fireStore)
}