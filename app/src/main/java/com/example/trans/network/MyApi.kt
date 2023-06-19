package com.example.trans.network

import com.example.trans.network.responses.AuthResponse
import com.example.trans.network.responses.QuotesResponse
import com.example.trans.network.responses.UserSaveBody
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @GET("configs")
    suspend fun getConfigDownload(@Query("currentConfigVersion") currentConfigVersion: String): Response<JsonObject>

    @GET("Company/isUserWhitelisted")
    suspend fun checkIfUserIsWhitelisted(@Query("phnNo") phoneNumber: String): Response<JsonObject>

    @GET("User/getUserData")
    suspend fun getUserData(@Query("phnNo") phoneNumber: String): Response<JsonObject>

    @POST("User/postUserData")
    suspend fun postUserData(@Body userSaveBody: UserSaveBody): Response<JsonObject>

    @GET("Product/getProductData")
    suspend fun getProductData(): Response<JsonObject>

}

