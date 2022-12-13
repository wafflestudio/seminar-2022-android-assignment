package com.example.simplecms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.simplecms.network.dto.PostDTO
import com.example.simplecms.network.dto.UserDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@Composable
fun PostListContainer(
    viewModel: PostListViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit
) {
    val postData = viewModel.pager.collectAsLazyPagingItems()
    val onCreatePost: suspend (title: String, content: String) -> Unit =
        { title, content ->
            viewModel.createPost(title = title, content = content)
            postData.refresh()
        }
    val onClickPost: (Int) -> Unit = navigateToDetail

    PostListPage(
        postData = postData,
        onCreatePost = onCreatePost,
        onClickPost = onClickPost
    )
}

@Composable
private fun PostListPage(
    postData: LazyPagingItems<PostDTO>,
    onCreatePost: suspend (title: String, content: String) -> Unit,
    onClickPost: (Int) -> Unit,
) {
    var isCreatePostDialogVisible by remember { mutableStateOf(false) }

    if (isCreatePostDialogVisible) {
        CreatePostDialog(
            onSubmit = { title, content ->
                onCreatePost(title, content)
                isCreatePostDialogVisible = false
            },
            onHide = { isCreatePostDialogVisible = false },
            scope = rememberCoroutineScope()
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Post List",
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                )

                IconButton(onClick = { isCreatePostDialogVisible = true }) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = "twitch"
                    )
                }
            }
        }

        LazyColumn {
            items(postData) { post ->
                post?.let {
                    PostItem(
                        post = post,
                        onClick = { onClickPost(post.id) }
                    )
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
}

@Composable
private fun PostItem(modifier: Modifier = Modifier, post: PostDTO, onClick: () -> Unit) {
    Card(
        modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.primary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.content,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val text = listOf(
                    "${post.comments.size} comments",
                    "by ${post.author.username}"
                ).joinToString("  •  ")

                Text(
                    text = text,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun ErrorItem(modifier: Modifier = Modifier, message: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        text = "Error occurred: ${message}",
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp),
        text = "• • •",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun CreatePostDialog(
    onSubmit: suspend (title: String, content: String) -> Unit,
    onHide: () -> Unit,
    scope: CoroutineScope,
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onHide() }) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colors.background)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Create Post",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colors.primary,
            )

            TextField(
                value = title,
                label = { Text(text = "Title") },
                onValueChange = { title = it }
            )
            TextField(value = content,
                label = { Text(text = "Content") },
                onValueChange = { content = it }
            )
            Button(onClick = {
                scope.launch {
                    onSubmit(title, content)
                }
            }) {
                Text(text = "Submit")
            }
        }
    }
}

@Preview
@Composable
private fun ItemPreview() {
    val items = listOf(
        PostDTO(
            id = 1,
            content = "가나다라마바사",
            title = "아자차카타파하",
            comments = emptyList(),
            createdAt = LocalDateTime.MAX,
            author = UserDTO(id = 1, username = "김상민")
        ),

        PostDTO(
            id = 1,
            content = "가나다라마바사",
            title = "아자차카타파하",
            comments = emptyList(),
            createdAt = LocalDateTime.MAX,
            author = UserDTO(id = 1, username = "김상민")
        ),

        PostDTO(
            id = 1,
            content = "가나다라마바사",
            title = "아자차카타파하",
            comments = emptyList(),
            createdAt = LocalDateTime.MAX,
            author = UserDTO(id = 1, username = "김상민")
        ),
    ).let {
        flowOf(PagingData.from(it)).collectAsLazyPagingItems()
    }

    PostListPage(
        postData = items,
        onCreatePost = { _, _ -> },
        onClickPost = {},
    )
}
