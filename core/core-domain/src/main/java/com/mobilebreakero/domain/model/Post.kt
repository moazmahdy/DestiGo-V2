package com.mobilebreakero.domain.model


data class Post(
    var id: String? = null,
    var userId: String? = null,
    var userName: String? = null,
    var location: String? = "Cairo, Egypt",
    var numberOfLikes: Int = 0,
    var text: String? = null,
    var comments: List<Comment>? = null,
    var image: String? = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
    var profilePhoto: String? = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
    val likedUserIds: List<String> = emptyList()
) {
    companion object {
        const val COLLECTION_NAME = "posts"
    }
}

data class Comment(
    var userId: String? = null,
    var userName: String? = null,
    var text: String? = null
)