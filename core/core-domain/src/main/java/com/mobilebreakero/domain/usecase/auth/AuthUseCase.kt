package com.mobilebreakero.domain.usecase.auth

data class AuthUseCase(
    val currentUser: CurrentUser,
    val getAuthState: GetAuthState,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val SignInAnnonymously: SignInAnnonymously,
    val signUpWithEmailAndPassword: SignUpWithEmailAndPassword,
    val signOut: SignOut,
    val reloadUser: ReloadUser,
    val sendPasswordResetEmail: SendPasswordResetEmail,
    val sendEmailVerification: SendEmailVerification,
    val updatePassword: UpdatePassword,
    val resetPassword: RestPassword,
    val updateEmail: UpdateEmail,
    val checkUserSignedIn: CheckUserSignedInUseCase,
    val deleteAccount: DeleteAccount
)