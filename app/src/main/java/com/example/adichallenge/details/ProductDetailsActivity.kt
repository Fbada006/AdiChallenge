package com.example.adichallenge.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adichallenge.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}