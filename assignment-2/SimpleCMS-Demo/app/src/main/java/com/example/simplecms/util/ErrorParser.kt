package com.example.simplecms.util

import com.example.simplecms.network.dto.ErrorDTO
import com.squareup.moshi.Moshi
import retrofit2.HttpException


fun HttpException.parseError(moshi: Moshi): ErrorDTO? {
    val rawStr = this.response()?.errorBody()?.string()
    try {
        return moshi.adapter(ErrorDTO::class.java).fromJson(rawStr)
    } catch (e: Exception) {
        return null
    }
}