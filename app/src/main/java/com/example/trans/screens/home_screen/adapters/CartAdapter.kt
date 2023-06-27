package com.example.trans.screens.home_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.trans.data.module.CartData
import com.example.trans.databinding.ItemCartBinding
import com.example.trans.network.enums.ClickType
import com.example.trans.utillity.logger.Logger


class CartAdapter constructor(
    private val glide: RequestManager,
    private val onItemClick: (CartData, ClickType) -> Unit
) :
    ListAdapter<CartData, CartViewHolder>(MyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(glide, binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class MyDiffCallback : DiffUtil.ItemCallback<CartData>() {
        override fun areItemsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem.prdId == newItem.prdId
        }

        override fun areContentsTheSame(oldItem: CartData, newItem: CartData): Boolean {
            return oldItem == newItem
        }
    }

}