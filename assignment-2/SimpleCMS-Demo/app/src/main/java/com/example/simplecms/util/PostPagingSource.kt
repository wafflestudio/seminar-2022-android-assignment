package com.example.simplecms.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.PostDTO

class PostPagingSource(
    private val restService: RestService
) : PagingSource<Int, PostDTO>() {

    override fun getRefreshKey(state: PagingState<Int, PostDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDTO> {
        val response = restService.getAllPostPaged(cursor = params.key, count = params.loadSize)
        val prevItemId = ((params.key ?: 0) - params.loadSize).let {
            if (it > 0) it
            else null
        }
        return LoadResult.Page(
            data = response.posts,
            nextKey = response.nextCursor,
            prevKey = prevItemId
        )
    }
}