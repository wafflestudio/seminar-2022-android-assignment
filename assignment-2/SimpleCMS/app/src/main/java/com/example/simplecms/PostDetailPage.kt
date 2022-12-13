package com.example.simplecms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecms.network.dto.CommentDTO
import com.example.simplecms.network.dto.PostDTO
import com.example.simplecms.network.dto.UserDTO
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter


@Composable
private fun PostDetailPage(
    post: PostDTO,
    onDeletePost: () -> Unit,
    onSubmitComment: (String) -> Unit,
    onDeleteComment: (Int) -> Unit,
) {
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
                    text = "Post List", style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onPrimary, fontWeight = FontWeight.Medium
                    )
                )
                IconButton(onClick = { onDeletePost() }) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "twitch"
                    )
                }
            }
        }

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .verticalScroll(scrollState),
        ) {

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = post.title,
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Medium,
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                val text = listOf(
                    "${post.comments.size} comments", "by ${post.author.username}"
                ).joinToString("  •  ")

                Text(
                    text = "at ${DateTimeFormatter.ISO_LOCAL_DATE.format(post.createdAt)}",
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )

                Text(
                    text = text,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption,
                )
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f)),
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = post.content,
                style = MaterialTheme.typography.body1,
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.onBackground.copy(alpha = 0.2f)),
            )

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "${post.comments.size} Comments",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
            )

            post.comments.forEach { item ->
                CommentItem(comment = item, isDeletable = false)
            }
        }
    }
}

@Composable
private fun CommentItem(comment: CommentDTO, isDeletable: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = comment.message,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isDeletable) {
                    IconButton(onClick = {}) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                            contentDescription = ""
                        )
                    }
                }

                val text = listOf(
                    "by ${comment.author.username}",
                    "at ${DateTimeFormatter.ISO_LOCAL_DATE.format(comment.createdAt)}"
                ).joinToString("  •  ")

                Text(
                    text = text,
                    maxLines = 1,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }


}

@Composable
@Preview
private fun PostDetailPagePreview() {
    val user1 = UserDTO(id = 3, username = "사용자 1")
    val user2 = UserDTO(id = 3, username = "사용자 2")
    val user3 = UserDTO(id = 3, username = "사용자 3")
    val time = LocalDateTime.of(2022, Month.MAY, 3, 3, 3)

    PostDetailPage(
        post = PostDTO(
            id = 1,
            content = "가나가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사가나다라마바사다라마바사",
            title = "아자차카타파하",
            comments = listOf(
                CommentDTO(
                    id = 1,
                    message = "댓글입니다 1",
                    author = user1,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 2,
                    message = "댓글입니다 2",
                    author = user3,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
                CommentDTO(
                    id = 3,
                    message = "댓글입니다 3",
                    author = user2,
                    createdAt = time,
                ),
            ),
            createdAt = time,
            author = UserDTO(id = 1, username = "김상민")
        ),
        onDeletePost = {},
        onSubmitComment = {},
        onDeleteComment = {},
    )
}

