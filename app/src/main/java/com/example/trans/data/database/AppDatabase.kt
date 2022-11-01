package com.example.trans.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trans.data.database.dao.ConfigDao
import com.example.trans.data.database.module.Config


@Database(
    entities = [Config::class],
    version = 2, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getConfigDao(): ConfigDao
}