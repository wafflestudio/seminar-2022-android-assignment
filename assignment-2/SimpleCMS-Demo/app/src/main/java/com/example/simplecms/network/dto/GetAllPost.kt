package com.example.simplecms.network.dto

data class GetAllPostResponse(
    val posts: List<PostDTO>,
    val nextCursor: Int?
)
