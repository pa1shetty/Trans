package com.example.trans.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.trans.utillity.AesLibrary
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class AppDataStore @Inject constructor(
    context: Context,
    private var aesLibrary: AesLibrary
) {
    private val dataStore = context.dataStore

     suspend fun saveString(key: Preferences.Key<String>, value: String="") {
        dataStore.edit { preference ->
            preference[key] = aesLibrary.encryptData(value)
        }
    }

     suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preference ->
            preference[key] = value
        }
    }


    suspend fun saveLastAppUpdateDate(lastAppUpdateDateValue: String) {
        saveString(PreferencesKeys.lastAppUpdateDate, lastAppUpdateDateValue)
    }

    suspend fun saveUpgradeFlag(upgradeFlag: String) {
        saveString(PreferencesKeys.upgradeFlag, upgradeFlag)
    }


    suspend fun saveConfigLastModifiedOn(lastConfigLastModifiedOnValue: String) {
        saveString(PreferencesKeys.configLastModifiedOn, lastConfigLastModifiedOnValue)
    }

    suspend fun saveCToken(cToken: String) {
        saveString(PreferencesKeys.cToken, cToken)
    }


    suspend fun saveSplTkn(cToken: String) {
        saveString(PreferencesKeys.splTkn, cToken)
    }


    private fun getInt(preferencesKey: Preferences.Key<Int>) =
        dataStore.data
            .map { preferences ->
                preferences[preferencesKey] ?: -1
            }

    fun getBoolean(preferencesKey: Preferences.Key<Boolean>, defaultValue: Boolean = false) =
        dataStore.data
            .map { preferences ->
                preferences[preferencesKey] ?: defaultValue
            }

    fun getString(preferencesKey: Preferences.Key<String>) =
        dataStore.data
            .map { preferences ->
                preferences[preferencesKey]?.let { aesLibrary.decryptData(it) } ?: ""
            }

}