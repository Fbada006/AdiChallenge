package com.example.adichallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adichallenge.models.Product
import com.example.adichallenge.repo.ProductRepository
import com.example.adichallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
) : ViewModel() {

    private var _products = MutableLiveData<Resource<List<Product>>>()

    val products: LiveData<Resource<List<Product>>> = _products

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
}