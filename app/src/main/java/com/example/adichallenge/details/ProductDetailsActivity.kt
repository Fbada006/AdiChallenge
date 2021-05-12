package com.example.adichallenge.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.adichallenge.R
import com.example.adichallenge.adapter.ReviewAdapter
import com.example.adichallenge.databinding.ActivityProductDetailsBinding
import com.example.adichallenge.main.ProductViewModel
import com.example.adichallenge.models.Product
import com.example.adichallenge.models.Review
import com.example.adichallenge.utils.EXTRA_PRODUCT
import com.example.adichallenge.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private val viewmodel by viewModels<ProductViewModel>()
    private var reviews = mutableListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewAdapter = ReviewAdapter(reviews)

        val product = intent?.getParcelableExtra<Product>(EXTRA_PRODUCT)
        product?.id?.let { viewmodel.getProductReviews(it) }
        populateUi(product)
        initRv()
        observeViewModelReviews()
    }

    private fun initRv() {
        binding.rvProductReviews.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(this@ProductDetailsActivity)
        }
    }

    private fun observeViewModelReviews() {
        viewmodel.reviews.observe(this, { reviewRes ->
            when (reviewRes) {
                is Resource.Failure -> {
                    showFailureUi()
                }
                is Resource.Loading -> {
                    showLoadingUi()
                }
                is Resource.Success -> {
                    showSuccessUi(reviewRes.data)
                }
            }
        })
    }

    private fun showSuccessUi(data: List<Review>) {
        binding.pbProductLoading.isVisible = false
        binding.tvError.isVisible = false
        binding.rvProductReviews.isVisible = true
        reviews.clear()
        reviews.addAll(data)
        reviewAdapter.notifyDataSetChanged()
    }

    private fun showLoadingUi() {
        binding.tvError.isVisible = false
        binding.rvProductReviews.isVisible = false
        binding.pbProductLoading.isVisible = true
    }

    private fun showFailureUi() {
        binding.pbProductLoading.isVisible = false
        binding.rvProductReviews.isVisible = false
        binding.tvError.isVisible = true
    }

    private fun populateUi(product: Product?) {
        binding.apply {
            ivProductImage.load("https://assets.adidas.com/images/w_320,h_320,f_auto,q_auto:sensitive,fl_lossy/3eebc0498b1347e397f8ab94016140ba_9366/FS1496_00_plp_standard.jpg")
            tvProductName.text = product?.name
            tvProductDescription.text = product?.description
            tvProductPrice.text = getString(R.string.product_price, product?.price)
        }
    }
}