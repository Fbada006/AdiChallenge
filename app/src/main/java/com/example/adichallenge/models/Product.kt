package com.example.adichallenge.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Product(
    val currency: String,
    val description: String,
    val id: String,
    val name: String,
    val price: Int
) : Parcelable