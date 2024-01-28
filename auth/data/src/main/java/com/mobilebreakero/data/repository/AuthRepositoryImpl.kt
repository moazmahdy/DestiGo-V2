package com.mobilebreakero.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.FirebaseFirestore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.typealiases.CheckUserSignedIn
import com.mobilebreakero.domain.typealiases.DeleteAccountResponse
import com.mobilebreakero.domain.typealiases.ReloadUserResponse
import com.mobilebreakero.domain.typealiases.ResetPasswordResponse
import com.mobilebreakero.domain.typealiases.SendResetPasswordResponse
import com.mobilebreakero.domain.typealiases.UpdateEmailResponse
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.await
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.inject.Inject
import javax.inject.Singleton
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: FireStoreRepository,
    private val fireStore: FirebaseFirestore
) : AuthRepository {

    override val currentUser get() = auth.currentUser

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        val userDoc = getCollection(AppUser.COLLECTION_NAME)
        val user = userDoc.whereEqualTo("email", email).get().await()
        if (user.isEmpty) {
            Failure(Exception("Email not found"))
        } else {
            auth.sendPasswordResetEmail(email).await()
            Success(true)
        }
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override suspend fun signInAnonymously() = try {
        auth.signInAnonymously().await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) = try {
        val userDoc = getCollection(AppUser.COLLECTION_NAME)
        val user = userDoc.whereEqualTo("email", email).get().await()

        if (user.isEmpty) {
            Failure(Exception("User not found"))
        } else {
            auth.signInWithEmailAndPassword(email.lowercase(), password).await()
            Success(true)
        }
    } catch (e: Exception) {
        Failure(e)
    }


    override suspend fun signUpWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                task.exception!!.localizedMessage?.let { Log.e("Error create user", it) }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    createFireStoreUser(task.result.user?.uid, name = name, email = email)
                }
            }
        }.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun signOut() = try {
        auth.signOut()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun deleteAccount(): DeleteAccountResponse = try {
        auth.currentUser?.delete()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun resetPassword(password: String): ResetPasswordResponse {
        return try {
            auth.currentUser?.updatePassword(password)?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun updateEmail(email: String): UpdateEmailResponse {
        return try {
            auth.currentUser?.verifyBeforeUpdateEmail(email)?.await()
            fireStore.collection("users").document(auth.currentUser?.uid ?: "")
                .update("email", email)
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendResetPassword(
        email: String,
        confirmationCode: Int
    ): SendResetPasswordResponse {
        return try {
            if (email.lowercase().trim() == auth.currentUser?.email?.lowercase()?.trim()) {
                val host = "smtp.gmail.com"
                val port = 587
                val username = "destigo6@gmail.com"
                val password = "qkehaeuxwqkjewsa"

                val props = Properties()
                props["mail.smtp.auth"] = "true"
                props["mail.smtp.starttls.enable"] = "true"
                props["mail.smtp.host"] = host
                props["mail.smtp.port"] = port

                val session = Session.getDefaultInstance(props, null)

                val message = MimeMessage(session)
                message.setFrom(InternetAddress(username))
                message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                message.subject = "Resetting Password for DestiGo Application"
                message.setText(
                    """
            Hello,
            
            Someone has requested to reset the password.
            
            Here's your confirmation code: $confirmationCode
            
            If you did not request it, please ignore this message.
            """.trimIndent()
                )

                val transport = session.getTransport("smtp")

                val result = withContext(Dispatchers.IO) {
                    transport.connect(host, username, password)
                    transport.sendMessage(message, message.allRecipients)
                    transport.close()
                    Success(true)
                }

                result
            } else {
                Failure(Exception("Email is the same as the current one \n please enter a different email"))
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun checkUserSignedIn(
        email: String,
        password: String,
        context: Context
    ): CheckUserSignedIn {
        return try {
            val currentUserEmail = auth.currentUser?.email?.lowercase()?.trim()
            if (email.lowercase().trim() == currentUserEmail) {
                signInWithEmailAndPassword(email, password)
                Success(true)
            } else {
                Failure(Exception("User is not the same as the current one \n please enter a different user"))
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }


    private suspend fun createFireStoreUser(uid: String?, name: String?, email: String?) {
        val user = AppUser(
            id = uid,
            name = name,
            email = email
        )
        repository.addUser(user, onSuccessListener = {
            DataUtils.user = user
        }) {

        }
    }

}