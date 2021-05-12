package com.example.adichallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adichallenge.adapter.ProductAdapter
import com.example.adichallenge.databinding.ActivityMainBinding
import com.example.adichallenge.models.Product
import com.example.adichallenge.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewmodel by viewModels<ProductViewModel>()
    private lateinit var productAdapter: ProductAdapter
    private var productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter(productList)
        initRv()
        observeViewModel()
    }

    private fun initRv() {
        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewmodel.products.observe(this, { productRes ->
            when (productRes) {
                is Resource.Failure -> {
                    showFailureUi()
                }
                is Resource.Loading -> {
                    showLoadingUi()
                }
                is Resource.Success -> {
                    showSuccessUi(productRes.data)
                }
            }
        })
    }

    private fun showSuccessUi(data: List<Product>) {
        binding.pbProductLoading.isVisible = false
        binding.tvError.isVisible = false
        binding.rvProduct.isVisible = true
        productList.clear()
        productList.addAll(data)
        productAdapter.notifyDataSetChanged()
    }

    private fun showLoadingUi() {
        binding.tvError.isVisible = false
        binding.rvProduct.isVisible = false
        binding.pbProductLoading.isVisible = true
    }

    private fun showFailureUi() {
        binding.pbProductLoading.isVisible = false
        binding.rvProduct.isVisible = false
        binding.tvError.isVisible = true
    }
}