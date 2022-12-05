package com.example.simplecms.network

import com.example.simplecms.network.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {
    @POST("/auth/login")
    suspend fun login(@Body() request: LoginRequest): LoginResult

    @GET("/post/:postId")
    suspend fun getPost(@Path("postId") postId: Int): GetPostResponse

    @GET("/post")
    suspend fun getAllPostPaged(
        @Query("cursor") cursor: Int? = null,
        @Query("count") count: Int = 10
    ): GetAllPostResponse

    @POST("/post")
    suspend fun createPost(@Body() request: CreatePostRequest): CreatePostResponse

    @POST("/post/:postId/comment")
    suspend fun createComment(
        @Body() request: CreateCommentRequest,
        @Path("postId") postId: Int
    ): CreateCommentResponse
}