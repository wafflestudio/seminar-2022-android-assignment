package com.example.simplecms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.CreateCommentRequest
import com.example.simplecms.network.dto.PostDTO
import com.example.simplecms.util.AuthStorage
import com.example.simplecms.util.Toaster
import kotlinx.coroutines.flow.*

class PostDetailViewModel(
    private val postId: Int,
    private val restService: RestService,
    private val toaster: Toaster,
    private val authStorage: AuthStorage,
) : ViewModel() {

    private val _post = MutableStateFlow<PostDTO?>(null)
    val post: StateFlow<PostDTO?> = _post
    val isOwner: StateFlow<Boolean> =
        _post.combine(authStorage.authInfo.map { it.user.id }) { post, userId ->
            post?.author?.id == userId
        }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = false)

    suspend fun refresh() {
        try {
            _post.value = restService.getPost(postId).result
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun deletePost() {
        try {
            restService.deletePost(postId)
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun createComment(message: String) {
        try {
            restService.createComment(CreateCommentRequest(message = message), postId = postId)
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun deleteComment(commentId: Int) {
        try {
            restService.deleteComment(commentId = commentId)
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }
}