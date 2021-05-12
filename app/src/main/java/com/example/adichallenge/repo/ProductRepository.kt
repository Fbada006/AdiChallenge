package com.example.adichallenge.repo

import com.example.adichallenge.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<List<Product>>
}