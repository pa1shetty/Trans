package com.example.trans.network

object NetworkConstants {

    const val CONNECTION_TIME_OUT = 1000 * 4
    const val READ_TIME_OUT = 1000 * 12

    const val mserver = "mserver"

    const val statusLbl = "status"
    const val versionLbl = "version"
    const val okLbl = "OK"
    const val errorCodeLbl = "errorCode"
    const val messageLbl = "message"
    const val msgLbl = "msg"
    const val dataLbl = "data"
    const val configLbl = "config"

    const val modifiedOnLbl = "modified_on"
    const val typeIdLbl = "type_id"
    const val phoneNumberLbl = "phoneNumber"

    const val otpLbl = "otp"
    const val cTokenLbl = "cToken"
    const val keyLbl = "key"
    const val splTknLbl = "splTkn"
    const val uat = "https://api.icarebuddy.com/Test/airavath/api/"
    fun getBaseUrl(urlType: Int = BaseUrlTypeEnum.Uat.baseUrlType): String {
        return when (urlType) {
            BaseUrlTypeEnum.PostMan.baseUrlType -> "https://d1492bfc-c1ff-4457-9009-bfeae5ee1486.mock.pstmn.io"
            BaseUrlTypeEnum.Uat.baseUrlType -> uat
            else -> {
                uat
            }
        }
    }

}

enum class RequestTypeEnum(val requestType: String) {
    IsUserWhitelisted("IsUserWhitelisted"),

}

@Suppress("unused")
enum class DownloadTypeEnum(val downloadType: String) {
    Configuration("3"),
}

enum class BaseUrlTypeEnum(val baseUrlType: Int) {
    Uat(0),
    PostMan(5)
}