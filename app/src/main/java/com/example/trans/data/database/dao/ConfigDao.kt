package com.example.trans.data.database.dao

import androidx.room.*
import com.example.trans.data.module.ConfigData

@Dao
@Entity
interface ConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveConfig(configData: List<ConfigData>)


    @Query("SELECT configValue FROM config WHERE configName = :configName")
    fun getConfig(configName: String): String

    @Query("SELECT * FROM config")
    @JvmSuppressWildcards
    fun getConfigs(): List<ConfigData>



    @Query("DELETE FROM config")
    fun nukeConfigurable()

}