package com.example.trans.screens.setup_screen.utils.configurations


import com.example.trans.data.module.ConfigData
import com.example.trans.data.database.DataBaseRepository
import com.example.trans.network.NetworkRepository
import com.example.trans.screens.setup_screen.utils.states.ConfigSetupState
import com.google.gson.JsonObject
import javax.inject.Inject


private const val MAX_PASSENGER_COUNT = "max_passenger_count"
private const val MAX_PASSENGER_COUNT_DEF = "6"

private const val MAX_MOBILE_NUMBER_DIGIT = "max_phone_number_digit"
private const val MAX_MOBILE_NUMBER_DIGIT_DEF = "10"

private const val MAX_OTP_LENGTH = "max_otp_length"
private const val MAX_OTP_LENGTH_DEF = "5"


private const val MAX_OTP_OTP_RESEND_WAIT = "resend_wait_second"
private const val MAX_OTP_OTP_RESEND_WAIT_DEF = "60"

private const val LOGIN_MANDATORY = "login_mandatory"
private const val LOGIN_MANDATORY_DEF = "0"

private const val MIN_PASSENGER_COUNT = "min_passenger_count"
private const val MIN_PASSENGER_COUNT_DEF = "2"
var configDataList = arrayListOf<ConfigData>()

class Configurations @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dataBaseRepository: DataBaseRepository
) {

    suspend fun setUpConfig(): ConfigSetupState {
        try {
            dataBaseRepository.saveConfig(getKeyValue(getConfigurationFromServer()))
        } catch (e: Exception) {
            configDataList = dataBaseRepository.getConfigs() as ArrayList<ConfigData>
            throw e
        }
        return ConfigSetupState.DONE
    }

    private fun getKeyValue(jsonObj: JsonObject): ArrayList<ConfigData> {
        jsonObj.keySet().forEach { keyStr ->
            configDataList.add(ConfigData(configName = keyStr, configValue = jsonObj[keyStr].asString))
        }
        return configDataList
    }

    private suspend fun getConfigurationFromServer(): JsonObject {
        return networkRepository.configDownload(currentConfigVersion())
    }

    private fun currentConfigVersion()
            : String {
        return "1"
    }
//    private suspend fun getConfigurationsFromDb(): List<Config> =
//        withContext(Dispatchers.IO) {
//            dataBaseRepository.getConfigs()
//        }

    private fun getConfiguration(key: String, defaultValue: String): String {
        for (configuration in configDataList) {
            if (configuration.configName.equals(key))
                return configuration.configValue!!
        }
        return defaultValue
    }

    fun getMaxPassengerCount(): Int {
        return Integer.valueOf(getConfiguration(MAX_PASSENGER_COUNT, MAX_PASSENGER_COUNT_DEF))
    }

    fun getMaxMobileNumberDigit(): Int {
        return Integer.valueOf(
            getConfiguration(
                MAX_MOBILE_NUMBER_DIGIT,
                MAX_MOBILE_NUMBER_DIGIT_DEF
            )
        )
    }

    fun getMaxOTPLength(): Int {
        return Integer.valueOf(getConfiguration(MAX_OTP_LENGTH, MAX_OTP_LENGTH_DEF))
    }

    fun getMaxOTPResendWait(): Int {
        return Integer.valueOf(
            getConfiguration(
                MAX_OTP_OTP_RESEND_WAIT,
                MAX_OTP_OTP_RESEND_WAIT_DEF
            )
        )
    }

    fun isLoginMandatory(): Boolean {
        return (Integer.valueOf(
            getConfiguration(
                LOGIN_MANDATORY,
                LOGIN_MANDATORY_DEF
            )
        ) == 1)
    }

    fun getMinPassengerCount(): Int {
        return Integer.valueOf(getConfiguration(MIN_PASSENGER_COUNT, MIN_PASSENGER_COUNT_DEF))
    }

    fun getConfigList() = configDataList
}