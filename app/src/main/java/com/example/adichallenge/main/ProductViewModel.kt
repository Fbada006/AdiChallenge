package com.example.adichallenge.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adichallenge.models.Product
import com.example.adichallenge.models.Review
import com.example.adichallenge.repo.ProductRepository
import com.example.adichallenge.utils.Event
import com.example.adichallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _navigateToSelectedProduct = MutableLiveData<Event<Product>>()

    val navigateToSelectedProduct: LiveData<Event<Product>>
        get() = _navigateToSelectedProduct

    private var _products = MutableLiveData<Resource<List<Product>>>()

    val products: LiveData<Resource<List<Product>>> = _products

    private var _reviews = MutableLiveData<Resource<List<Review>>>()

    val reviews: LiveData<Resource<List<Review>>> = _reviews

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts()
                .onStart {
                    _products.value = Resource.Loading()
                }.catch { error ->
                    _products.value = Resource.Failure(error)
                }.collect { data ->
                    _products.value = Resource.Success(data)
                }
        }
    }

    fun displayDeliveryDetails(data: Product) {
        _navigateToSelectedProduct.value = Event(data)
    }

    fun getProductReviews(id: String) {
        viewModelScope.launch {
            productRepository.getProductReviewsById(id)
                .onStart {
                    _reviews.value = Resource.Loading()
                }.catch { error ->
                    _reviews.value = Resource.Failure(error)
                }.collect { data ->
                    _reviews.value = Resource.Success(data)
                }
        }
    }
}