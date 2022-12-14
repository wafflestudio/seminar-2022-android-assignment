package com.example.simplecms.network

import com.example.simplecms.network.dto.*
import retrofit2.Response
import retrofit2.http.*

interface RestService {
    @POST("/auth/login")
    suspend fun login(@Body() request: LoginRequest): LoginResult

    @GET("/post/{postId}")
    suspend fun getPost(@Path("postId") postId: Int): GetPostResponse

    @GET("/post")
    suspend fun getAllPostPaged(
        @Query("cursor") cursor: Int? = null,
        @Query("count") count: Int = 10
    ): GetAllPostResponse

    @POST("/post")
    suspend fun createPost(@Body() request: CreatePostRequest): CreatePostResponse

    @POST("/post/{postId}/comment")
    suspend fun createComment(
        @Body() request: CreateCommentRequest,
        @Path("postId") postId: Int
    ): CreateCommentResponse

    // TODO: update on deletion
    @DELETE("/post/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int): Response<Unit>

    @DELETE("/post/comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int): Response<Unit>
}