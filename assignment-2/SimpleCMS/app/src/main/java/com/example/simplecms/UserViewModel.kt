package com.example.simplecms

import androidx.lifecycle.ViewModel
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.LoginRequest
import com.example.simplecms.network.dto.UserDTO
import com.example.simplecms.util.AuthStorage
import com.example.simplecms.util.Toaster
import retrofit2.HttpException

class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    suspend fun login(username: String, password: String) {
        try {
            val response = restService.login(LoginRequest(username, password))
            authStorage.setAuthInfo(response.accessToken, UserDTO(1, "temp"))
        } catch (e: Exception) {
            val message = (e as? HttpException)?.message() ?: "Unknown error occurred"
            toaster.toast(message)
        }
    }

}
