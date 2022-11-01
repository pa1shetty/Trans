package com.example.trans.network

object NetworkConstants {

    const val CONNECTION_TIME_OUT = 1000 * 4
    const val READ_TIME_OUT = 1000 * 12

    const val mserver = "mserver"

    const val requestTypeLbl = "requestType"
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

    fun getBaseUrl(urlType: Int = BaseUrlTypeEnum.Uat.baseUrlType): String {
        return when (urlType) {
            BaseUrlTypeEnum.PavanLocal.baseUrlType -> "http://10.8.0.10:8084/bwi_mobile/"
            BaseUrlTypeEnum.ONE_NOT_ONE.baseUrlType -> "https://auminfotech.in/bwi_mobile/"
            BaseUrlTypeEnum.MockLab.baseUrlType -> "https://nammametromvvm.mocklab.io"
            BaseUrlTypeEnum.PostMan.baseUrlType -> "https://e58d0ec4-f6a5-4ebe-9ee5-c517da7bf3dc.mock.pstmn.io"

            else -> {
                "http://uatmetro.auminfotech.in:7004/bwi_mobile/"
            }
        }
    }

}

enum class RequestTypeEnum(val requestType: String) {
    @Suppress("unused")
    CheckForUpdate("115"),
    Download("113"),
    RequestForOtp("103"),
    VerifyOtp("104"),
    Register("105"),
    FetchTicketList("121"),

}
@Suppress("unused")
enum class DownloadTypeEnum(val downloadType: String) {
    Configuration("3"),
}

enum class BaseUrlTypeEnum(val baseUrlType: Int) {
    Uat(0),
    PavanLocal(1),
    ONE_NOT_ONE(2),
    MockLab(4),
    PostMan(5)


}