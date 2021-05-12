package com.example.adichallenge.network

import com.example.adichallenge.models.Product
import com.example.adichallenge.models.Review
import retrofit2.http.GET
import retrofit2.http.Path

interface AdidasProductService {
    @GET("product")
    suspend fun getAllProducts(): List<Product>

    @GET("reviews/{product_id}")
    suspend fun getProductReviews(
        @Path("product_id") productId: String
    ): List<Review>
}
