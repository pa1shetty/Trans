package com.example.trans.screens.home_screen.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.trans.data.module.CartData
import com.example.trans.databinding.ItemCartBinding
import com.example.trans.network.enums.ClickType
import com.example.trans.utillity.UtilClass
import com.example.trans.utillity.UtilsClassUI

class CartViewHolder constructor(
    private val glide: RequestManager,
    private val binding: ItemCartBinding,
    private val onItemClick: (CartData, ClickType) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CartData) {
        binding.tvPrdName.text = item.prdName
        glide.load(item.prdImage).into(binding.ivPrd)
        binding.tvPrdAmt.text = "₹${item.prdAmount * item.prdQnty}"
        binding.tvPrdCount.text = item.prdQnty.toString()
        binding.tvAddPrd.setOnClickListener { onItemClick(item, ClickType.IncreaseProduct) }
        binding.tvRemovePrd.setOnClickListener { onItemClick(item, ClickType.DecreaseProduct) }
    }
}