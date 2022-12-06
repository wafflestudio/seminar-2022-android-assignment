package com.example.simplecms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.simplecms.util.PostPagingSource

class PostListViewModel(
    private val postPagingSource: PostPagingSource
) : ViewModel() {

    val pager = Pager(PagingConfig(pageSize = 15)) {
        postPagingSource
    }.flow.cachedIn(viewModelScope)

}