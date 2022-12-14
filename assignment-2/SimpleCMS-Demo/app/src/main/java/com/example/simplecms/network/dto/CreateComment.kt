package com.example.simplecms.network.dto

data class CreateCommentRequest(
    val message: String,
)

data class CreateCommentResponse(
    val result: PostDTO
)
