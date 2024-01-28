package com.mobilebreakero.domain.model


data class Trip(
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var location: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var duration: String? = null,
    var why: String? = null,
    var image: String? = "https://firebasestorage.googleapis.com/v0/b/destigo-84de1.appspot.com/o/placeholder.png?alt=media&token=39cb8a01-600b-4b3e-85b9-d86625fd7b59",
    var checkList: List<CheckList>? = null,
    var places: List<TripPlace>? = null,
    var tripImages: List<TripPhotos>? = null,
    var tripJournal: List<TripJournal>? = null,
    var category: List<String>? = null,
    var finished: Boolean? = null,
    var isSaved: Boolean? = null,
) {
    companion object {
        const val COLLECTION_NAME = "trips"
    }
}

data class CheckList(
    var name: String? = null,
    var checkItemId: String? = null,
    val checked: Boolean? = null,
)

data class TripPhotos(
    var tripId: String? = null,
    var images: String? = null,
)

data class TripJournal(
    var id: String? = null,
    var userId: String? = null,
    var tripId: String? = null,
    var title: String? = null,
    var content: String? = null,
    var image: String? = null,
    var date: String? = null,
    var isSaved: Boolean? = null,
) {
    companion object {
        const val COLLECTION_NAME = "tripJournal"
    }
}


data class TripPlace(
    var id: String? = null,
    var userId: String? = null,
    var tripId: String? = null,
    var name: String? = null,
    var location: String? = null,
    var image: String? = null,
    var date: String? = null,
    var isSaved: Boolean? = null,
    var isVisited: Boolean? = null,
)