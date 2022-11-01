package com.example.trans.database

import com.example.nammametromvvm.data.repositaries.database.module.Config
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val db: AppDatabase,
) {

    fun saveConfig(config: List<Config>) {
        db.getConfigDao().nukeConfigurable()
        db.getConfigDao().saveConfig(config)
    }

    fun getConfigs() = db.getConfigDao().getConfigs()

    fun getConfig(key: String) = db.getConfigDao().getConfig(key)

    fun clearDb() = db.clearAllTables()

}