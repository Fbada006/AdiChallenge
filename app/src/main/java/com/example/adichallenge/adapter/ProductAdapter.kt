package com.example.adichallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.adichallenge.R
import com.example.adichallenge.databinding.ProductItemBinding
import com.example.adichallenge.models.Product

@Suppress("UNCHECKED_CAST")
class ProductAdapter(
    private val products: List<Product>?,
    private val clickListener: OnProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var productListFiltered: List<Product>?

    init {
        productListFiltered = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productListFiltered?.get(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(product!!)
        }
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productListFiltered?.size ?: 0
    }

    class ProductViewHolder constructor(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            binding.tvProductName.text = product?.name
            binding.tvProductDescription.text = product?.description
            binding.tvProductPrice.text =
                itemView.context.getString(R.string.product_price, product?.price)
            binding.ivProductImage.load("https://assets.adidas.com/images/w_320,h_320,f_auto,q_auto:sensitive,fl_lossy/3eebc0498b1347e397f8ab94016140ba_9366/FS1496_00_plp_standard.jpg")
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val binding =
                    ProductItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ProductViewHolder(binding)
            }
        }
    }

    class OnProductClickListener(val clickListener: (data: Product) -> Unit) {
        fun onClick(data: Product) = clickListener(data)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.isNullOrEmpty()) {
                    productListFiltered = products
                } else {
                    val filteredList = mutableListOf<Product>()
                    products?.forEach { product ->
                        if ((product.name == constraint) || (product.description == constraint)) {
                            filteredList.add(product)
                        }
                    }
                    productListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = productListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productListFiltered = results?.values as List<Product>?
            }
        }
    }
}