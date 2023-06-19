package com.example.trans.data.database.dao

import androidx.room.*
import com.example.trans.data.module.CartData
import kotlinx.coroutines.flow.Flow

@Dao
@Entity
interface CartDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCartProduct(cartData: CartData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCartProducts(cartData: List<CartData>)

    @Query("SELECT * FROM cart WHERE prdId = :prdId")
    fun getCartProduct(prdId: Int): CartData

    @Query(
        "SELECT * FROM cart"
    )
    fun getCartProducts(): Flow<List<CartData>>

    @Query("DELETE FROM cart WHERE prdId = :prdId")
    fun deleteCartProduct(prdId: Int)

    @Query("DELETE FROM cart")
    fun nukeCartProducts()

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE prdId = :prdId)")
    fun cartProductExists(prdId: Int): Boolean

    @Query("SELECT * FROM cart WHERE prdId = :prdId")
    fun getProductFromCart(prdId: Int): CartData
}