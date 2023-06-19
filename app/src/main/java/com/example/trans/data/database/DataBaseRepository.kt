package com.example.trans.data.database

import com.example.trans.data.module.CartData
import com.example.trans.data.module.ConfigData
import com.example.trans.data.module.ProductData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val db: AppDatabase,
) {

    fun saveConfig(configData: List<ConfigData>) {
        db.getConfigDao().nukeConfigurable()
        db.getConfigDao().saveConfig(configData)
    }

    fun getConfigs() = db.getConfigDao().getConfigs()

    fun getConfig(key: String) = db.getConfigDao().getConfig(key)

    fun clearDb() = db.clearAllTables()
    val allProducts: Flow<List<ProductData>> = db.getProductDao().getProducts()
    fun saveProducts(productData: List<ProductData>) {
        db.getProductDao().saveProducts(productData)
    }

    fun clearProductsTable() = db.getProductDao().nukeProducts()

    fun doesPrdExistInCart(prdId: Int): Boolean = db.getCartDao().cartProductExists(prdId)
    fun deletePrdFromCart(prdId: Int) = db.getCartDao().deleteCartProduct(prdId)
    fun nukeCartProducts() = db.getCartDao().nukeCartProducts()


    fun saveCartProduct(cartData: CartData) = db.getCartDao().saveCartProduct(cartData)

    val allCartProducts: Flow<List<CartData>> = db.getCartDao().getCartProducts()
    fun userName(userId: String) = db.getUserDetailsDao().getUserName(userId)

    fun getProductFromCart(prdId: Int) = db.getCartDao().getProductFromCart(prdId)
}