package com.mobilebreakero.domain.util


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
//import com.google.firebase.firestore.CollectionReference
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}

fun getCollection (collectionName:String): CollectionReference {
    val db = Firebase.firestore
    return  db.collection(collectionName)
}