package com.example.adichallenge.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    val text: String
)