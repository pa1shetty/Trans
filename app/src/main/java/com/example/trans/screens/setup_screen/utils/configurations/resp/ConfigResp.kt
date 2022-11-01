package com.example.trans.screens.setup_screen.utils.configurations.resp

data class ConfigResp(
    val status: String,
    val data: ConfigData,
    val errorCode: String,
    val errorMsg: String,
)