package com.example.trans.network

import com.example.trans.data.module.ProductData
import com.example.trans.network.responses.IsUserWhitelistedResponse
import com.example.trans.network.responses.UserData
import com.example.trans.network.responses.UserDetails
import com.example.trans.network.responses.UserSaveBody
import com.example.trans.utillity.logger.NetworkLogger
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.inject.Inject


class NetworkRepository @Inject constructor(
    private val api: MyApi,
    private val gson: Gson
) : SafeApiRequest(), NetworkInterface {

    override suspend fun configDownload(currentConfigVersion: String): JsonObject {
        NetworkLogger.INSTANCE.debug { "configDownload: currentConfigVersion:$currentConfigVersion" }
        return apiRequest { api.getConfigDownload(currentConfigVersion) }.getAsJsonObject("data")
    }

    override suspend fun checkIfUserIsWhitelisted(phoneNumber: String): Boolean {
        NetworkLogger.INSTANCE.debug { "checkIfUserIsWhitelisted: phoneNumber:$phoneNumber" }
        return gson.fromJson(getData(apiRequest {
            api.checkIfUserIsWhitelisted(phoneNumber)
        }), IsUserWhitelistedResponse::class.java).isUserWhitelisted
    }

    override suspend fun getUserData(phoneNumber: String): UserData {
        NetworkLogger.INSTANCE.debug { "getUserData: phoneNumber:$phoneNumber" }
        return gson.fromJson(getData(apiRequest {
            api.getUserData(phoneNumber)
        }), UserData::class.java)
    }

    override suspend fun sendUserData(userDetails: UserDetails): Boolean {
        NetworkLogger.INSTANCE.debug { "sendUserData: userDetails:$userDetails" }
        apiRequest {
            api.postUserData(
                UserSaveBody(
                    usrLat = userDetails.userLat,
                    usrLong = userDetails.usrLong,
                    usrId = userDetails.usrId,
                    usrName = userDetails.usrName,
                    usrAddrs = userDetails.usrAddrs,
                    usrPhnNo = userDetails.usrPhnNo.toLong()
                )
            )
        }
        return true
    }

    override suspend fun getProductsData(): List<ProductData> {
        NetworkLogger.INSTANCE.debug { "getProductsData:" }
        return gson.fromJson(getData(apiRequest {
            api.getProductData()
        }).getAsJsonArray("products"),  Array<ProductData>::class.java).toList()
    }

    private fun getData(response: JsonObject): JsonObject = response.getAsJsonObject("data")
}