package com.example.trans.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trans.database.dao.ConfigDao
import com.example.nammametromvvm.data.repositaries.database.module.Config


@Database(
    entities = [Config::class],
    version = 2, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getConfigDao(): ConfigDao
}