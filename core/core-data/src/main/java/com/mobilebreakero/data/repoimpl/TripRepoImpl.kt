package com.mobilebreakero.data.repoimpl

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObjects
import com.mobilebreakero.domain.model.CheckList
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripJournal
import com.mobilebreakero.domain.model.TripPhotos
import com.mobilebreakero.domain.model.TripPlace
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.repo.getPublicTripsResponse
import com.mobilebreakero.domain.repo.getTripJournalDetailsResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.updatePlacesResponse
import com.mobilebreakero.domain.repo.updateTripResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepoImpl @Inject constructor() : TripsRepo {

    override suspend fun getTrips(id: String): getTripsResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripQuery = tripCollection.whereEqualTo("userId", id)
            val tripQuerySnapshot = tripQuery.get().await()

            if (tripQuerySnapshot.isEmpty) {
                Response.Failure(NoSuchElementException("No trips found"))
            } else {
                val trips = tripQuerySnapshot.toObjects<Trip>()
                Response.Success(trips)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addTrip(
        trip: Trip,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse = try {
        val tripCollection = getCollection(Trip.COLLECTION_NAME)
        val tripDoc = tripCollection.document(trip.id!!)
        tripDoc.set(trip)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addCheckList(
        itemName: String,
        id: String,
        checked: Boolean,
        checkItemId: String
    ): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)

            val newCheckList = CheckList(
                name = itemName,
                checked = checked,
                checkItemId = checkItemId
            )

            val tripDoc = tripCollection.document(id)
            val existingTrip = tripDoc.get().await().toObject(Trip::class.java)

            existingTrip?.let {
                tripDoc.update("checkList", FieldValue.arrayUnion(newCheckList))
            }

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addPlaces(
        placeName: String,
        placeId: String,
        id: String,
        placeTripId: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)

            val newPlace = TripPlace(
                name = placeName,
                tripId = id,
                location = placeId,
                id = placeTripId
            )

            val tripDoc = tripCollection.document(id)
            val existingTrip = tripDoc.get().await().toObject(Trip::class.java)

            existingTrip?.let {
                tripDoc.update("places", FieldValue.arrayUnion(newPlace))
            }

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override fun updatePhoto(photo: String, id: String): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            tripDoc.update("image", photo)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun deleteTrip(id: String): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            tripDoc.delete()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getTripDetails(id: String): Response<Trip> {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            val trip = tripDoc.get().await().toObject(Trip::class.java)
            Response.Success(trip!!)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getTripsByCategory(categories: String): getTripsResponse {
        return try {
            val db = FirebaseFirestore.getInstance()

            val tripDoc = db.collection(Trip.COLLECTION_NAME)
                .whereArrayContains("category", categories)
                .get()
                .await()

            if (!tripDoc.isEmpty) {
                val trips = tripDoc.toObjects<Trip>()
                Response.Success(trips)
            } else {
                Response.Failure(Exception("No trips found for the specified category"))
            }
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch trips", e))
        }
    }

    override suspend fun updatePhotoPlace(
        photo: String, id: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            val newPhoto = TripPhotos(
                tripId = id,
                images = photo
            )
            tripDoc.update("tripImages", FieldValue.arrayUnion(newPhoto))
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun isPlaceVisited(
        isVisited: Boolean,
        placeId: String,
        tripId: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("places", FieldValue.arrayUnion(isVisited))
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun addPlaceVisitDate(
        date: String,
        placeId: String,
        tripId: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            val updateMap = mapOf(
                "places.$placeId.date" to date
            )
            tripDoc.set(updateMap, SetOptions.merge())

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun addTripJournal(
        journal: String,
        journalId: String,
        tripId: String,
        title: String,
        image: String,
        date: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            val newJournal = TripJournal(
                id = journalId,
                title = title,
                content = journal,
                image = image,
                tripId = tripId,
                date = date
            )
            tripDoc.update("tripJournal", FieldValue.arrayUnion(newJournal))
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun savePublicTrip(
        trip: TripsItem,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse {
        return try {
            val tripCollection = getCollection(TripsItem.COLLECTION_NAME)
            val tripDoc = tripCollection.document(trip.tripId)
            tripDoc.set(trip)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getPublicTrips(userId: String): getPublicTripsResponse {
        return try {
            val tripCollection = getCollection(TripsItem.COLLECTION_NAME)
            val tripQuery = tripCollection.whereEqualTo("userId", userId)
            val tripQuerySnapshot = tripQuery.get().await()

            if (tripQuerySnapshot.isEmpty) {
                Response.Failure(NoSuchElementException("No trips found"))
            } else {
                val trips = tripQuerySnapshot.toObjects<TripsItem>()
                Response.Success(trips)
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateTripName(tripName: String, tripId: String): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("name", tripName)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateTripDate(tripDate: String, tripId: String): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("startDate", tripDate)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateTripDays(tripDays: String, tripId: String): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("duration", tripDays)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun isTripFinished(tripId: String, finished: Boolean): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("finished", finished)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getTripJournalDetails(
        tripId: String,
        journalId: String
    ): getTripJournalDetailsResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)

            val tripJournalDoc = tripDoc.collection(TripJournal.COLLECTION_NAME).document(journalId)

            val tripJournal = tripJournalDoc.get().await().toObject(TripJournal::class.java)

            if (tripJournal != null) {
                Response.Success(tripJournal)
            } else {
                Response.Failure(Exception("Trip journal not found"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


}