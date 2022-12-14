package com.example.simplecms.network.dto

data class CreatePostRequest(
    val content: String,
    val title: String,
)

data class CreatePostResponse(
    val result: PostDTO,
)
