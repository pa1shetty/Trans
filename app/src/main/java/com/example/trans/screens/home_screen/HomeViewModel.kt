package com.example.trans.screens.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.database.DataBaseRepository
import com.example.trans.data.datastore.DataStoreRepository
import com.example.trans.data.module.CartData
import com.example.trans.data.module.ProductData
import com.example.trans.network.NetworkRepository
import com.example.trans.network.enums.ClickType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkInterface: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val allProducts: Flow<List<ProductData>> = dataBaseRepository.allProducts
    val allCartProducts: Flow<List<CartData>> = dataBaseRepository.allCartProducts
    private val _userName = MutableSharedFlow<String>(replay = 0)
    private val _cartAmount = MutableStateFlow(0)
    val cartAmount = _cartAmount.asStateFlow()
    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount.asStateFlow()

    init {
        getProducts()
        gerUserName()
        getCartAmountAndItemCount()
    }

    private fun getCartAmountAndItemCount() {
        viewModelScope.launch {
            allCartProducts.collect { productList ->
                _cartAmount.emit(getCartAmount(productList))
                _cartItemCount.emit(productList.size)
            }
        }
    }

    private fun getCartAmount(cartList: List<CartData>): Int {
        var totalAmount=0
        cartList.forEach { cartData ->
            totalAmount+=cartData.prdQnty*cartData.prdAmount
        }
return totalAmount
    }

    private fun gerUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _userName.emit(dataBaseRepository.userName(dataStoreRepository.userId.first()))
        }
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataBaseRepository.clearProductsTable()
                dataBaseRepository.saveProducts(networkInterface.getProductsData())
            } catch (e: Exception) {
                Log.d("test123", "getProducts: " + e.message)
            }
        }
    }


    fun addProductToCart(cartData: CartData) {
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.saveCartProduct(
                cartData
            )
        }
    }

    fun changeProductCount(cartData: CartData, clickType: ClickType) {
        viewModelScope.launch(Dispatchers.IO) {
            when (clickType) {
                ClickType.DecreaseProduct -> {
                    cartData.prdQnty = cartData.prdQnty - 1
                }

                ClickType.IncreaseProduct -> {
                    cartData.prdQnty = cartData.prdQnty + 1
                }
            }
            if (cartData.prdQnty > 0) {
                dataBaseRepository.saveCartProduct(
                    cartData
                )
            } else {
                dataBaseRepository.deletePrdFromCart(cartData.prdId)
            }
        }
    }

    fun removeProductFromCart(prdId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.deletePrdFromCart(prdId)
        }
    }

    fun getPrdDetailsFromCart(productData: ProductData): CartData {
        return dataBaseRepository.getProductFromCart(productData.prdId) ?: CartData(
            prdId = productData.prdId,
            prdQnty = 1,
            prdAmount = productData.prdAmount,
            prdName = productData.prdName,
            prdDesc = productData.prdDesc,
            prdImage = productData.prdImage,
            prdStatus = productData.prdStatus,
            prdMinOrderQnty = productData.prdMinOrderQnty,
            prdMaxOrderQnty = productData.prdMaxOrderQnty
        )
    }

    fun getCartSize(cartList: List<CartData>) = cartList.size
}

