package com.example.trans.network

import com.google.gson.JsonObject


interface NetworkInterface {

    suspend fun configDownload(currentConfigVersion: String): JsonObject

}