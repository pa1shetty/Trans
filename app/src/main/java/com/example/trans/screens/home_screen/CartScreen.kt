package com.example.trans.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.trans.R
import com.example.trans.databinding.CartScreenBinding
import com.example.trans.screens.home_screen.adapters.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartScreen : Fragment() {
    @Inject
    lateinit var glide: RequestManager
    private val viewModel by viewModels<HomeViewModel>()
    private val binding: CartScreenBinding by lazy {
        CartScreenBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observe()
        binding.radioButton1.isChecked = true
    }

    private lateinit var adapter: CartAdapter
    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(glide, onItemClick = { cartData, clickType ->
            viewModel.changeProductCount(cartData, clickType)
        })
        binding.recyclerView.adapter = adapter
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.allCartProducts.collect {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.cartAmount.collect { cartAmount ->
                binding.tvAmount.text = getString(
                    R.string.amount_text,
                    cartAmount
                )

            }
        }
        lifecycleScope.launch {
            viewModel.isCartIsEmpty.collect { isCartIsEmpty ->
                if (isCartIsEmpty) {
                    binding.clCartDetails.visibility = View.GONE
                    binding.clEmptyCart.visibility = View.VISIBLE

                } else {
                    binding.clEmptyCart.visibility = View.GONE
                    binding.clCartDetails.visibility = View.VISIBLE

                }
            }
        }

        lifecycleScope.launch {
            viewModel.cartItemCount.collect { cartItemCount ->
                binding.tvItems.text = resources.getQuantityString(
                    R.plurals.items_in_cart_v2,
                    cartItemCount,
                    cartItemCount
                )
            }
        }
    }
}