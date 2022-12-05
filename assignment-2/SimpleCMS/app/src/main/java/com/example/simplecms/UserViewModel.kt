package com.example.simplecms

import androidx.lifecycle.ViewModel
import com.example.simplecms.network.RestService

class UserViewModel(
    private val restService: RestService
) : ViewModel() {
}