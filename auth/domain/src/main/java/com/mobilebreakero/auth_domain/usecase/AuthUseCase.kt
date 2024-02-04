package com.mobilebreakero.auth_domain.usecase

data class AuthUseCase(
    val currentUser: CurrentUser,
    val getAuthState: GetAuthState,
    val signInWithEmailAndPassword: SignInWithEmailAndPassword,
    val signInAnonymously: SignInAnnonymously,
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