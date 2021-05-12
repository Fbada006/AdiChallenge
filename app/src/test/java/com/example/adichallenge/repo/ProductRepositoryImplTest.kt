package com.example.adichallenge.repo

import com.example.adichallenge.models.Product
import com.example.adichallenge.network.AdidasProductService
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ProductRepositoryImplTest {

    private val apiService: AdidasProductService = mockk(relaxed = true)
    private val repo = ProductRepositoryImpl(apiService)

    @Test
    fun `get all products should return proper data`() {
        val products = listOf(
            Product(currency = "kes", description = "hello", id = "123", name = "", price = 1),
            Product(
                currency = "dollar",
                description = "hello 2",
                id = "1233",
                name = "",
                price = 12
            )
        )
        coEvery { repo.getAllProducts() } returns mockk() {
            flow {
                mockk() {
                    emit(products)
                }
            }
        }

        runBlocking {
            repo.getAllProducts().collect {
                Truth.assertThat(it.size).isEqualTo(2)
            }
        }
    }
}