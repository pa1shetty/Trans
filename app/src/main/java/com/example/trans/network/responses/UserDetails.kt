package com.example.trans.network.responses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.trans.network.Enums.RegStatus

@Entity(tableName = "user")
data class UserDetails(
    var usrAddrs: String = "",
    @PrimaryKey(autoGenerate = false)
    var usrId: String = "",
    var usrName: String = "",
    var usrPhnNo: String = "",
    var usrShopName: String = "",
    @Ignore
    var usrLong: Double = 0.0,
    @Ignore
    var userLat: Double = 0.0,
    var regStatus: Int = RegStatus.UN_REG.status
)