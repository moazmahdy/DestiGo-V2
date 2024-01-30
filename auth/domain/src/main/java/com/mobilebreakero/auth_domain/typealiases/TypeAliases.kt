package com.mobilebreakero.auth_domain.typealiases

import com.mobilebreakero.auth_domain.util.Response
import kotlinx.coroutines.flow.StateFlow

typealias AuthStateResponse = StateFlow<Boolean>
typealias SignInResponse = Response<Boolean>
typealias SignUpResponse = Response<Boolean>
typealias SignOutResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>
typealias ResetPasswordResponse = Response<Boolean>
typealias SendResetPasswordResponse = Response<Boolean>
typealias UpdateEmailResponse = Response<Boolean>
typealias CheckUserSignedIn = Response<Boolean>
typealias DeleteAccountResponse = Response<Boolean>