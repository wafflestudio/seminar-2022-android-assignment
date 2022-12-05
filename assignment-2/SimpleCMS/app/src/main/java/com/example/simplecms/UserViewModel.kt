package com.example.simplecms

import androidx.lifecycle.ViewModel
import com.example.simplecms.network.RestService
import com.example.simplecms.network.dto.LoginRequest
import com.example.simplecms.util.AuthStorage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import retrofit2.HttpException

class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
) : ViewModel() {

    private val _errorEvent = MutableSharedFlow<Event>()
    val eventStream: SharedFlow<Event> = _errorEvent

    suspend fun login(username: String, password: String) {
        try {
            val response = restService.login(LoginRequest(username, password))
            authStorage.setAccessToken(response.accessToken)
            _errorEvent.tryEmit(Event.SignInSucceed)
        } catch (e: Exception) {
            _errorEvent.tryEmit(
                Event.Error(
                    if (e is HttpException) e.message() ?: "알 수 없는 에러가 발생했습니다."
                    else "알 수 없는 에러가 발생했습니다."
                )
            )
        }
    }

    sealed class Event {
        data class Error(val msg: String) : Event()
        object SignInSucceed : Event()
    }
}
