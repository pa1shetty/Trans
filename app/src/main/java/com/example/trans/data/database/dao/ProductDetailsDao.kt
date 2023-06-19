package com.example.trans.data.database.dao

import androidx.room.*
import com.example.trans.data.module.ProductData
import kotlinx.coroutines.flow.Flow

@Dao
@Entity
interface ProductDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: ProductData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProducts(products: List<ProductData>)

    @Query("SELECT * FROM products WHERE prdId = :prdId")
    fun getProduct(prdId: Int): ProductData

    @Query(
        "SELECT * FROM products"
    )
    fun getProducts(): Flow<List<ProductData>>

    @Query("DELETE FROM products")
    fun nukeProducts()

}