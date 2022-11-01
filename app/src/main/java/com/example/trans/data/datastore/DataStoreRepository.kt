package com.example.trans.data.datastore

import androidx.datastore.dataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(private val dataStore: AppDataStore) {
    val isLoggedIn = dataStore.getBoolean(PreferencesKeys.userLoggedIn)
    val isLoggInSkipped = dataStore.getBoolean(PreferencesKeys.loginSkipped, false)
    val userId = dataStore.getString(PreferencesKeys.userId)

    suspend fun userLoggedIn() = dataStore.saveBoolean(PreferencesKeys.userLoggedIn, true)
    suspend fun userLoggedOut() = dataStore.saveBoolean(PreferencesKeys.userLoggedIn, false)
    suspend fun saveUserId(userId:String) = dataStore.saveString(PreferencesKeys.userId)

}