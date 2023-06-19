package com.example.trans.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductData(
    @PrimaryKey
    val prdId: Int,
    val prdName: String,
    val prdDesc: String,
    val prdImage: String,
    val prdStatus: Int,
    val prdAmount: Int,
    val prdAvlQnty: Int,
    var prdInCartQnty:Int =0,
    var prdMinOrderQnty:Int,
    var prdMaxOrderQnty:Int

    )