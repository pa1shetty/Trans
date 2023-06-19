package com.example.trans.network

import com.example.trans.utillity.logger.NetworkLogger
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            val jsonObject = JSONObject(response.body().toString())
            if (jsonObject.getString("status").equals("OK")) {
                NetworkLogger.INSTANCE.debug { "Response: ${response.body().toString()}" }
                return response.body()!!
            } else {
                NetworkLogger.INSTANCE.error { "Error:  ${jsonObject.getString("errorMsg")}" }
                throw ErrorException(
                    jsonObject.getString("errorMsg"),
                    jsonObject.getInt("errorCode")
                )
            }
        } else {
            val error = response.errorBody().toString()
            val message = StringBuilder()

            error.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (_: JSONException) {
                }
                message.append("\n")
            }
            NetworkLogger.INSTANCE.error { message }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

}