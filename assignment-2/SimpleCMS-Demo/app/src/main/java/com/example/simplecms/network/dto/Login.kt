package com.example.simplecms.network.dto

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResult(
    val accessToken: String
)

