package com.example.trans.screens.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trans.databinding.CartScreenBinding
import com.example.trans.screens.home_screen.adapters.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartScreen : Fragment() {

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
    }

    private lateinit var adapter: CartAdapter
    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(onItemClick = { cartData, clickType ->
            viewModel.changeProductCount(cartData, clickType)
        })
        binding.recyclerView.adapter = adapter
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.allCartProducts.collect {
                adapter.submitList(it)
            }
        }
    }
}