package com.example.trans.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trans.data.database.dao.ConfigDao
import com.example.trans.data.database.dao.UserDetailsDao
import com.example.trans.data.database.module.Config
import com.example.trans.network.responses.UserDetails


@Database(
    entities = [Config::class, UserDetails::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getConfigDao(): ConfigDao
    abstract fun getUserDetailsDao(): UserDetailsDao
}