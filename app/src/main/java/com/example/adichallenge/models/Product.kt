package com.example.adichallenge.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val currency: String,
    val description: String,
    val id: String,
    val name: String,
    val price: Int
)