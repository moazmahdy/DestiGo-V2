package com.mobilebreakero.profile.yourposts

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.repo.updatePostResponse
import com.mobilebreakero.domain.usecase.firestore.PostUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourPostsViewModel @Inject constructor(
    private val useCase: PostUseCase
) : ViewModel() {

    val currentUser = Firebase.auth.currentUser?.uid

    init {
        getPosts(userId = currentUser ?: "")
    }

    private val _postsFlow = MutableStateFlow<postResponse>(Response.Loading)
    val postsFlow: StateFlow<postResponse> get() = _postsFlow

    var postsResult by mutableStateOf(listOf<Post>())

    fun getPosts(userId: String) {
        viewModelScope.launch {
            try {
                val result = useCase.getPostsByUserId(userId)
                if (result is Response.Success) {
                    val posts = result.data
                    postsResult = posts
                    _postsFlow.value = Response.Success(posts)
                } else {
                    _postsFlow.value = result
                }
            } catch (e: Exception) {
                _postsFlow.value = Response.Failure(e)
            }
        }
    }


    private var updateLikesResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
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
                updateLikesResponse = useCase.likePost(postId, likes, userId, context)
            } catch (e: Exception) {
                updateLikesResponse = Response.Failure(e)
            }
        }
    }

    var deletePostResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun deletePost(postId: String) {
        viewModelScope.launch {
            try {
                deletePostResponse = Response.Loading
                deletePostResponse = useCase.deletePost(postId)
            } catch (e: Exception) {
                deletePostResponse = Response.Failure(e)
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
                    useCase.sharePost(postId = postId, userId = userId, userName = userName)
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
                addCommentResponse = useCase.addComment(
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


}