package com.example.adichallenge.repo

import com.example.adichallenge.models.Product
import com.example.adichallenge.models.Review
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<List<Product>>
    suspend fun getProductReviewsById(id: String): Flow<List<Review>>
}