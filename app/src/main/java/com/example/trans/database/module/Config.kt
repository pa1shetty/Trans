package com.example.nammametromvvm.data.repositaries.database.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class Config(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var configName: String? = null,
    var configValue: String? = null
)
