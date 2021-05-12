package com.example.adichallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adichallenge.databinding.ReviewItemBinding
import com.example.adichallenge.models.Review

class ReviewAdapter(
    private val reviews: List<Review>?
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews?.get(position)
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviews?.size ?: 0
    }

    class ReviewViewHolder constructor(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review?) {
            binding.tvReview.text = review?.text
        }

        companion object {
            fun from(parent: ViewGroup): ReviewViewHolder {
                val binding =
                    ReviewItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ReviewViewHolder(binding)
            }
        }
    }
}