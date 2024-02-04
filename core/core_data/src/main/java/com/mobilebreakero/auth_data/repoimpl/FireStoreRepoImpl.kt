package com.mobilebreakero.auth_data.repoimpl

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.mobilebreakero.auth_domain.model.AppUser
import com.mobilebreakero.auth_domain.model.RecommendedPlaceItem
import com.mobilebreakero.auth_domain.model.Trip
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.FireStoreRepository
import com.mobilebreakero.auth_domain.repo.addUserResponse
import com.mobilebreakero.auth_domain.repo.savedPlacesResponse
import com.mobilebreakero.auth_domain.repo.savedTripsResponse
import com.mobilebreakero.auth_domain.repo.tripsResponseInterested
import com.mobilebreakero.auth_domain.repo.updateUserResponse
import com.mobilebreakero.auth_domain.repo.userResponse
import com.mobilebreakero.auth_domain.util.Response
import com.mobilebreakero.auth_domain.util.getCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepoImpl @Inject constructor() : FireStoreRepository {

    override suspend fun getUsers(): Flow<Response.Success<MutableList<AppUser>>> = flow {
        val userCollection = getCollection(AppUser.COLLECTION_NAME)
        val users = userCollection.get().await().toObjects(AppUser::class.java)
        emit(Response.Success(users))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateUserStatues(id: String, status: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("status", status)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUserLocation(id: String, location: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("location", location)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUserPhotoUrl(id: String, photoUrl: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("photoUrl", photoUrl)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUserById(id: String): userResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()

            if (userDocument.exists()) {
                val appUser = userDocument.toObject(AppUser::class.java)
                appUser?.let { Response.Success(it) }
                    ?: Response.Failure(Exception("User document is null"))
            } else {
                Response.Failure(Exception("User document with ID $id not found"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addUser(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(user.id!!)
            userDoc.set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUser(id: String, name: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("name", name)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun updateUserInterestedPlaces(
        id: String,
        interestedPlaces: List<String>
    ): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("interestedPlaces", interestedPlaces)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUserTripsBasedOnInterestedPlaces(id: String): tripsResponseInterested {
        return try {
            val db = FirebaseFirestore.getInstance()

            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()
            val user = userDocument.toObject(AppUser::class.java)
            val userInterests = user?.interestedPlaces
            Log.e("userInterests", userInterests.toString())

            val trips = mutableListOf<Trip>()

            if (userInterests != null) {
                for (interestedPlace in userInterests) {
                    val tripDoc = db.collection(Trip.COLLECTION_NAME)
                        .whereArrayContains("category", interestedPlace)
                        .get()
                        .await()

                    Log.e(
                        "tripDoc",
                        "Interested Place: $interestedPlace, Result: ${tripDoc.documents}"
                    )

                    if (!tripDoc.isEmpty) {
                        trips.addAll(tripDoc.toObjects())
                    }
                }

                if (trips.isNotEmpty()) {
                    Response.Success(trips)
                } else {
                    Response.Failure(Exception("No trips found for the specified categories"))
                }
            } else {
                Response.Failure(Exception("User's interested places list is null"))
            }
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch trips", e))
        }
    }

    override suspend fun updateUserSaved(
        id: String,
        savePlaces: RecommendedPlaceItem?,
        savedTrips: TripsItem?
    ): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            if (savePlaces != null) {
                userDoc.update("savedPlaces", FieldValue.arrayUnion(savePlaces))
            }
            if (savedTrips != null) {
                userDoc.update("savedTrips", FieldValue.arrayUnion(savedTrips))
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUserSavedPlaces(id: String): savedPlacesResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userSavedPlacesQuery = userCollection.whereEqualTo("id", id).get().await()

            if (!userSavedPlacesQuery.isEmpty) {
                val userDocument = userSavedPlacesQuery.documents[0]
                val appUser = userDocument.toObject(AppUser::class.java)

                if (appUser != null) {
                    val savedPlaces = appUser.savedPlaces
                    Log.e("userSavedPlaces", savedPlaces.toString())

                    if (savedPlaces != null) {
                        Response.Success(savedPlaces)
                    } else {
                        Response.Failure(Exception("User's saved places list is null"))
                    }
                } else {
                    Response.Failure(Exception("Failed to convert document to AppUser object"))
                }
            } else {
                Response.Failure(Exception("User document with id $id not found"))
            }
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch saved places", e))
        }
    }

    override suspend fun getUserSavedTrips(id: String): savedTripsResponse {
        return try {
            val tripCollection = getCollection(TripsItem.COLLECTION_NAME)
            val tripQuery = tripCollection.whereEqualTo("userId", id)
            Log.d("FirestoreDebug", "Query: $tripQuery")
            val tripQuerySnapshot = tripQuery.get().await()

            if (tripQuerySnapshot.isEmpty) {
                Log.d("FirestoreDebug", "No trips found for userId: $id")
                Response.Failure(NoSuchElementException("No trips found"))
            } else {
                val trips = tripQuerySnapshot.toObjects<TripsItem>()
                Response.Success(trips)
            }
        } catch (e: Exception) {
            Log.e("FirestoreDebug", "Error in getTrips: $e")
            Response.Failure(e)
        }
    }
}
