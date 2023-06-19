package com.example.trans.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trans.data.database.dao.CartDetailsDao
import com.example.trans.data.database.dao.ConfigDao
import com.example.trans.data.database.dao.ProductDetailsDao
import com.example.trans.data.database.dao.UserDetailsDao
import com.example.trans.data.module.CartData
import com.example.trans.data.module.ConfigData
import com.example.trans.data.module.ProductData
import com.example.trans.network.responses.UserDetails


@Database(
    entities = [ConfigData::class, UserDetails::class, ProductData::class,CartData::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getConfigDao(): ConfigDao
    abstract fun getUserDetailsDao(): UserDetailsDao

    abstract fun getProductDao(): ProductDetailsDao

    abstract fun getCartDao(): CartDetailsDao


}