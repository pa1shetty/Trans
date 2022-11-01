package com.example.trans.network

import com.example.trans.network.responses.AuthResponse
import com.example.trans.network.responses.QuotesResponse
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

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    @GET("configs")
    suspend fun getConfigDownload(@Query("currentConfigVersion") currentConfigVersion: String): Response<JsonObject>


}

