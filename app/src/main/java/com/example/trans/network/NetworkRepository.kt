package com.example.trans.network

import com.example.trans.network.responses.IsUserWhitelistedResponse
import com.example.trans.network.responses.UserData
import com.example.trans.network.responses.UserDetails
import com.example.trans.network.responses.UserSaveBody
import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val api: MyApi,
    private val gson: Gson
) : SafeApiRequest(), NetworkInterface {

    override suspend fun configDownload(currentConfigVersion: String): JsonObject {
        return apiRequest { api.getConfigDownload(currentConfigVersion) }.getAsJsonObject("data")
    }

    override suspend fun checkIfUserIsWhitelisted(phoneNumber: String): Boolean {
        return gson.fromJson(getData(apiRequest {
            api.checkIfUserIsWhitelisted(phoneNumber)
        }), IsUserWhitelistedResponse::class.java).isUserWhitelisted
    }

    override suspend fun getUserData(phoneNumber: String): UserData {
        return gson.fromJson(getData(apiRequest {
            api.getUserData(phoneNumber)
        }), UserData::class.java)
    }

    override suspend fun sendUserData(userDetails: UserDetails): Boolean {
        apiRequest {
            api.postUserData(
                UserSaveBody(
                    usrlat = userDetails.userLat,
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

    private fun getData(response: JsonObject): JsonObject = response.getAsJsonObject("data")
}