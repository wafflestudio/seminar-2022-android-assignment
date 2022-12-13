package com.example.simplecms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.CreatePostRequest
import com.example.simplecms.util.PostPagingSource
import com.example.simplecms.util.Toaster

class PostListViewModel(
    private val postPagingSource: PostPagingSource,
    private val restService: RestService,
    private val toaster: Toaster,
) : ViewModel() {

    val pager = Pager(PagingConfig(pageSize = 15)) {
        postPagingSource
    }.flow.cachedIn(viewModelScope)

    suspend fun createPost(content: String, title: String) {
        try {
            restService.createPost(
                CreatePostRequest(
                    content = content,
                    title = title
                )
            )
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }

    suspend fun deletePost(id: Int) {
        try {
            restService.deletePost(postId = id)
        } catch (e: Exception) {
            toaster.toast(e.message ?: "Unknown error occurred")
        }
    }
}