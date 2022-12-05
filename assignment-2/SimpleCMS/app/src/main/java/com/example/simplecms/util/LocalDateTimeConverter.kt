package com.example.simplecms.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

    @FromJson()
    fun fromJson(raw: String): LocalDateTime {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(raw) as LocalDateTime
    }

    @ToJson()
    fun toJson(dateTime: LocalDateTime): String {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime)
    }
}