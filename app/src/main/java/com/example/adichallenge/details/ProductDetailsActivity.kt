package com.example.adichallenge.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.adichallenge.R
import com.example.adichallenge.databinding.ActivityProductDetailsBinding
import com.example.adichallenge.models.Product
import com.example.adichallenge.utils.EXTRA_PRODUCT

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent?.getParcelableExtra<Product>(EXTRA_PRODUCT)
        populateUi(product)
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