package com.mobilebreakero.auth_domain.model


data class AppUser(
    var id: String? = null,
    var name: String? = "User",
    var email: String? = null,
    var status: String? = "no status added",
    var location: String? = "no location added",
    var savedPlaces: List<RecommendedPlaceItem>? = listOf(),
    var savedTrips: List<TripsItem>? = listOf(),
    var interestedPlaces: List<String>? = listOf(),
    var visitedPlaces: List<String>? = listOf(),
    var likedPosts: List<String>? = listOf(),
    var photoUrl: String? = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
) {
    companion object {
        const val COLLECTION_NAME = "users"
    }
}