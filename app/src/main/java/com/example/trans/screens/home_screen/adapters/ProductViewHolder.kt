package com.example.trans.screens.home_screen.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.trans.R
import com.example.trans.data.module.ProductData
import com.example.trans.databinding.ItemProductBinding
import com.example.trans.network.enums.ProductStatus

class ProductViewHolder(
    private val glide: RequestManager,
    private val binding: ItemProductBinding,
    private val onItemClick: (ProductData) -> Unit,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ProductData) {
        binding.tvPrdctName.text = item.prdName
        binding.tvPrdctDesc.text = item.prdDesc
        binding.tvPrdctAmount.text = context.getString(R.string.amount, item.prdAmount.toString())
        glide.load(item.prdImage).into(binding.ivProduct)
        binding.btn.isSelected = true
        when (item.prdStatus) {
            ProductStatus.IN_STOCK.statusCode -> {
            }

            ProductStatus.OUT_OF_STOCK.statusCode -> {
                binding.btn.text = context.getString(R.string.out_of_stock)
                binding.btn.isEnabled = false
            }

            ProductStatus.COMING_SOON.statusCode -> {
                binding.btn.text = context.getString(R.string.coming_soon)
                binding.btn.isEnabled = false
            }
        }
        binding.btn.setOnClickListener { onItemClick(item) }

    }
}