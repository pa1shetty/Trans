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
import com.example.trans.databinding.HomeScreenBinding
import com.example.trans.screens.bottomsheet.BottomSheet
import com.example.trans.screens.home_screen.adapters.ProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    lateinit var binding: HomeScreenBinding

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var bottomSheet: BottomSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var adapter: ProductAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        //showViewCartSnackBar()
        setUpViewCartStrip()
        observe()

    }

    private fun setUpViewCartStrip() {
        binding.viewCartButton.setOnClickListener {
            val activity = requireActivity()
            val bottomNavigationView =
                activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.selectedItemId = R.id.cartScreen
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter(glide, onItemClick = {
            lifecycleScope.launch(Dispatchers.IO) {
                val cartData = viewModel.getPrdDetailsFromCart(it)
                withContext(Dispatchers.Main) {
                    bottomSheet.bottomSheetAddToCart(cartData, addProductToCart = { cartData ->
                        viewModel.addProductToCart(cartData)
                    }, removeProductFromCart = { prdId ->
                        viewModel.removeProductFromCart(prdId)
                    })
                }
            }
        })
        binding.recyclerView.adapter = adapter
    }


    private fun observe() {
        lifecycleScope.launch {
            viewModel.allProducts.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.allCartProducts.collect { cartList ->
                if (cartList.isNotEmpty()) {
                    binding.tvCartDetails.text = resources.getQuantityString(
                        R.plurals.items_in_cart,
                        viewModel.getCartSize(cartList),
                        viewModel.getCartSize(cartList),
                        viewModel.getCartAmount(cartList)
                    )
                    binding.viewCartStripe.visibility = View.VISIBLE
                } else {
                    binding.viewCartStripe.visibility = View.GONE
                }
            }
        }
    }
}