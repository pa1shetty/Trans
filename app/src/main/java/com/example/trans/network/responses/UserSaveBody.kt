package com.example.trans.network.responses



data class UserSaveBody(
    val id: Int = 0,
    val usrAddrs: String = "",
    val usrId: String = "",
    val usrLong: Double = 0.0,
    val usrName: String = "",
    val usrPhnNo: Long = 0,
    val usrShopName: String = "",
    val usrlat: Double = 0.0
)