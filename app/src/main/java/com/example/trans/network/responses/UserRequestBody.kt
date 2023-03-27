package com.example.trans.network.responses

import com.google.gson.annotations.SerializedName


class UserDetailsRequestBody(
    @field:SerializedName("usrAddrs")
    private val usrAddrs: String,
    @field:SerializedName("usrId")
    private val usrId: String,
    @field:SerializedName("usrName")
    private val usrName: String,
    @field:SerializedName("usrLong")
    val usrLong: Double = 0.0,
    @field:SerializedName("userLat")
    val userLat: Double = 0.0,
    @field:SerializedName("usrPhnNo")
    val usrPhnNo: String = "",
)
