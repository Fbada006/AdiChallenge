package com.example.adichallenge.repo

import com.example.adichallenge.models.Product
import com.example.adichallenge.network.AdidasProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: AdidasProductService
) : ProductRepository {

    override suspend fun getAllProducts(): Flow<List<Product>> =
        flow {
            emit(productService.getAllProducts())
        }
}