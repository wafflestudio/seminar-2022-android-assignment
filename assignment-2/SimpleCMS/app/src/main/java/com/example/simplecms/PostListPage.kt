package com.example.simplecms

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.simplecms.network.dto.PostDTO
import org.koin.androidx.compose.koinViewModel


@Composable
fun PostListPage(viewModel: PostListViewModel = koinViewModel()) {
    val postData = viewModel.pager.collectAsLazyPagingItems()

    LazyColumn {
        items(postData) { post ->
            post?.let {
                PostItem(post = post)
            }
        }
        when (postData.loadState.append) {
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item { LoadingItem() }
            }
            is LoadState.Error -> {
                item {
                    ErrorItem(message = (postData.loadState.append as LoadState.Error).error.message.toString())
                }
            }
        }
    }
}

@Composable
private fun PostItem(post: PostDTO) {
}

@Composable
private fun ErrorItem(message: String) {
}

@Composable
private fun LoadingItem() {
}
