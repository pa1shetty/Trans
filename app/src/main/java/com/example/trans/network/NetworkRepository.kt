package com.example.trans.network

import com.google.gson.JsonObject
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val api: MyApi,
) : SafeApiRequest(), NetworkInterface {

    override suspend fun configDownload(currentConfigVersion: String): JsonObject {
        return apiRequest { api.getConfigDownload(currentConfigVersion) }.getAsJsonObject("data")
    }
}