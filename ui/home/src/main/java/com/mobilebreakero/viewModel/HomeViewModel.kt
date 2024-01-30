package com.mobilebreakero.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.auth_domain.model.Post
import com.mobilebreakero.auth_domain.model.RecommendedPlaceItem
import com.mobilebreakero.auth_domain.model.ReviewItem
import com.mobilebreakero.auth_domain.model.TripsItem
import com.mobilebreakero.auth_domain.repo.addPostResponse
import com.mobilebreakero.auth_domain.repo.addTripResponse
import com.mobilebreakero.auth_domain.repo.getPublicTripsResponse
import com.mobilebreakero.auth_domain.repo.postDetailsResponse
import com.mobilebreakero.auth_domain.repo.postResponse
import com.mobilebreakero.auth_domain.repo.updatePostResponse
import com.mobilebreakero.auth_domain.repo.updateUserResponse
import com.mobilebreakero.auth_domain.usecase.GetReviewsUseCase
import com.mobilebreakero.auth_domain.usecase.RecommendedPlaceUseCase
import com.mobilebreakero.auth_domain.usecase.RecommendedUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.PostUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.auth_domain.usecase.firestore.UserUseCase
import com.mobilebreakero.auth_domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val userUseCase: UserUseCase,
    private val recommendedUseCase: RecommendedUseCase,
    private val recommendedPlaceUseCase: RecommendedPlaceUseCase,
    private val tripsUseCase: TripsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
) : ViewModel() {

    init {
        getPosts()
    }

    private val _postsFlow = MutableStateFlow<postResponse>(Response.Loading)
    val postsFlow: StateFlow<postResponse> get() = _postsFlow

    fun getPosts() {
        viewModelScope.launch {
            try {
                val result = postUseCase.getPosts()
                if (result is Response.Success) {
                    val posts = result.data
                    _postsFlow.value = Response.Success(posts)
                } else {
                    _postsFlow.value = result
                }
            } catch (e: Exception) {
                _postsFlow.value = Response.Failure(e)
            }
        }
    }

    private val _postsIdFlow = MutableStateFlow<postResponse>(Response.Loading)
    val postsIdFlow: StateFlow<postResponse> get() = _postsFlow

    var postsResult by mutableStateOf(listOf<Post>())

    fun getPostsById(userId: String) {
        viewModelScope.launch {
            try {
                val result = postUseCase.getPostsByUserId(userId)
                if (result is Response.Success) {
                    val posts = result.data
                    postsResult = posts
                    _postsIdFlow.value = Response.Success(posts)
                } else {
                    _postsIdFlow.value = result
                }
            } catch (e: Exception) {
                _postsIdFlow.value = Response.Failure(e)
            }
        }
    }

    private val _publicTripsFlow = MutableStateFlow<getPublicTripsResponse>(Response.Loading)
    val publicTripsFlow: StateFlow<getPublicTripsResponse> get() = _publicTripsFlow

    var publicTripResult by mutableStateOf(listOf<TripsItem>())

    fun getPublicTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getPublicTrips(userId)
                if (result is Response.Success) {
                    val trips = result.data
                    publicTripResult = trips
                    _publicTripsFlow.value = Response.Success(trips)
                } else {
                    _publicTripsFlow.value = result

                }
            } catch (e: Exception) {
                _publicTripsFlow.value = Response.Failure(e)
            }
        }
    }

    var updateLikesResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun likePost(
        postId: String,
        likes: Int,
        userId: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                updateLikesResponse = Response.Loading
                updateLikesResponse = postUseCase.likePost(postId, likes, userId, context)
            } catch (e: Exception) {
                updateLikesResponse = Response.Failure(e)
            }
        }
    }

    var sharePostResponse by mutableStateOf<addPostResponse>(Response.Success(false))
        private set

    fun sharePost(postId: String, userId: String, userName: String) {
        viewModelScope.launch {
            try {
                sharePostResponse = Response.Loading
                sharePostResponse =
                    postUseCase.sharePost(postId = postId, userId = userId, userName = userName)
            } catch (e: Exception) {
                sharePostResponse = Response.Failure(e)
            }
        }
    }


    var addCommentResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun addComment(postId: String, comment: String, userId: String, userName: String) {
        viewModelScope.launch {
            try {
                addCommentResponse = Response.Loading
                addCommentResponse = postUseCase.addComment(
                    id = postId,
                    comment = comment,
                    userId = userId,
                    userName = userName
                )
            } catch (e: Exception) {
                addCommentResponse = Response.Failure(e)
            }
        }
    }


    private val details =
        MutableStateFlow<postDetailsResponse>(Response.Success(Post()))
    val detailsResult: StateFlow<postDetailsResponse> = details


    fun getPostDetails(id: String) {
        viewModelScope.launch {
            try {
                details.value = Response.Loading
                val result = postUseCase.getPostDetails(id)
                details.value = result
            } catch (e: Exception) {
                details.value = Response.Failure(e)
            }
        }
    }


    var userRecommendations by mutableStateOf(listOf<TripsItem?>())
        private set

    fun getRecommendations(userInterests: List<String>) {
        viewModelScope.launch {
            try {
                val result = recommendedUseCase.getRecommendation(userInterests)
                userRecommendations = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting recommendations: $e")
            }
        }
    }

    var userRecommendationsPlaces by mutableStateOf(listOf<RecommendedPlaceItem?>())
        private set

    fun getRecommendedPlaces(userInterests: List<String>) {
        viewModelScope.launch {
            try {
                val result = recommendedPlaceUseCase.getRecommendationPlaces(userInterests)
                userRecommendationsPlaces = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting recommendations: $e")
            }
        }
    }

    var updateUserSaved by mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun updateSaves(
        id: String,
        savePlaces: RecommendedPlaceItem? = null,
        savedTrips: TripsItem? = null
    ) {
        viewModelScope.launch {
            try {
                val result = userUseCase.updateUserSaved(id, savePlaces, savedTrips)
                updateUserSaved = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting recommendations: $e")
            }
        }
    }

    var savePublicTripsResponse by mutableStateOf<addTripResponse>(Response.Success(false))
        private set

    fun savePublicTrips(trip: TripsItem) {
        viewModelScope.launch {
            try {
                savePublicTripsResponse = Response.Loading
                savePublicTripsResponse = tripsUseCase.savePublicTrips(trip, {}, {})
            } catch (e: Exception) {
                savePublicTripsResponse = Response.Failure(e)
            }
        }
    }

    var deletePostResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun deletePost(postId: String) {
        viewModelScope.launch {
            try {
                deletePostResponse = Response.Loading
                deletePostResponse = postUseCase.deletePost(postId)
            } catch (e: Exception) {
                deletePostResponse = Response.Failure(e)
            }
        }
    }

    var getReviews by mutableStateOf(listOf(ReviewItem()))
        private set

    fun getReviews() {
        viewModelScope.launch {
            return@launch try {
                val result = getReviewsUseCase.invoke()
                getReviews = result
            } catch (e: Exception) {
                getReviews = emptyList()
            }
        }
    }
}