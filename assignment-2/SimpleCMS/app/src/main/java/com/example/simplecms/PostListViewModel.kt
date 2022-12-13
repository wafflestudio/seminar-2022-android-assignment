package com.example.simplecms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.CreatePostRequest
import com.example.simplecms.util.PostPagingSource
import com.example.simplecms.util.Toaster

class PostListViewModel(
    private val restService: RestService,
    private val toaster: Toaster,
) : ViewModel() {

    val pager = Pager(PagingConfig(pageSize = 15)) {
        PostPagingSource(restService)
    }.flow.cachedIn(viewModelScope)

    suspend fun createPost(content: String, title: String) {
        try {
            restService.createPost(
                CreatePostRequest(
                    content = content,
                    title = title
                )
            )
            toaster.toast("Successfully created.")
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
}