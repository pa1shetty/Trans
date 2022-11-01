package com.example.trans.network.responses

data class ServerResp(
    val status: String,
    val data: Any,
    val errorCode: String,
    val errorMsg: String,
)