package com.example.adichallenge.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adichallenge.R
import com.example.adichallenge.adapter.ProductAdapter
import com.example.adichallenge.databinding.ActivityMainBinding
import com.example.adichallenge.details.ProductDetailsActivity
import com.example.adichallenge.models.Product
import com.example.adichallenge.utils.EXTRA_PRODUCT
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

        productAdapter = ProductAdapter(productList, ProductAdapter.OnProductClickListener { data ->
            viewmodel.displayDeliveryDetails(data)
        })
        initRv()
        observeViewModelForProducts()
        observeViewModelForNavigation()
    }

    private fun observeViewModelForNavigation() {
        viewmodel.navigateToSelectedProduct.observe(this, { productEvent ->
            productEvent.getContentIfNotHandled()?.let { product ->
                val intent = Intent(this, ProductDetailsActivity::class.java)
                intent.putExtra(EXTRA_PRODUCT, product)
                startActivity(intent)
            }
        })
    }

    private fun initRv() {
        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModelForProducts() {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager =
            (getSystemService(Context.SEARCH_SERVICE)) as SearchManager

        val search = menu.findItem(R.id.action_search)
            .actionView as SearchView
        search.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        search.maxWidth = Int.MAX_VALUE

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                productAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                productAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
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