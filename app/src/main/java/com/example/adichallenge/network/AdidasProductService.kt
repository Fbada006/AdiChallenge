package com.example.adichallenge.network

import com.example.adichallenge.models.Product
import retrofit2.http.GET

interface AdidasProductService {
    @GET("product")
    suspend fun getAllProducts(): List<Product>
}
