package com.example.trans.data.module

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart"
)
data class CartData(
    @PrimaryKey(autoGenerate = true)
    val cartPrdId: Int = 0,
    val prdId: Int,
    val prdName: String,
    val prdDesc: String,
    val prdImage: String,
    val prdStatus: Int,
    val prdAmount: Int,
    var prdQnty: Int,
    var prdInCartQnty: Int = 0,
    var prdMinOrderQnty: Int,
    var prdMaxOrderQnty: Int
)

//foreignKeys = [ForeignKey(
//entity = ProductData::class,
//parentColumns = arrayOf("prdId"),
//childColumns = arrayOf("prdId"),
//onDelete = ForeignKey.CASCADE
//)]