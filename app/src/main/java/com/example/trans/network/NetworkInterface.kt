package com.example.trans.network

import com.example.trans.data.module.ProductData
import com.example.trans.network.responses.UserData
import com.example.trans.network.responses.UserDetails
import com.google.gson.JsonObject


interface NetworkInterface {

    suspend fun configDownload(currentConfigVersion: String): JsonObject
    suspend fun checkIfUserIsWhitelisted(phoneNumber: String): Boolean

    suspend fun getUserData(phoneNumber: String): UserData
    suspend fun sendUserData(userDetails: UserDetails): Boolean

    suspend fun getProductsData(): List<ProductData>

}