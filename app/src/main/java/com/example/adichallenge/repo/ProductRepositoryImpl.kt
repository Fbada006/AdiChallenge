package com.example.adichallenge.repo

import com.example.adichallenge.models.Product
import com.example.adichallenge.network.AdidasProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val productService: AdidasProductService
) : ProductRepository {

    override suspend fun getAllProducts(): Flow<List<Product>> =
        flow {
            emit(productService.getAllProducts())
        }
}