package com.example.simplecms.util

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthStorage(
    context: Context
) {
    private val sharedPref =
        context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE)

    private val _accessToken = MutableStateFlow(sharedPref.getString(AccessTokenKey, "")!!)
    val accessToken: StateFlow<String> = _accessToken

    fun setAccessToken(token: String) {
        _accessToken.value = token
        sharedPref.edit {
            putString(AccessTokenKey, token)
        }
    }

    companion object {
        const val AccessTokenKey = "access_token"
        const val SharedPreferenceName = "auth_pref"
    }
}