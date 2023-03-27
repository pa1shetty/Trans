package com.example.trans.data.datastore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepository @Inject constructor(private val dataStore: AppDataStore) {
    val isLoggedIn = dataStore.getBoolean(PreferencesKeys.userLoggedIn)
    val isLoggInSkipped = dataStore.getBoolean(PreferencesKeys.loginSkipped, false)
    val userId = dataStore.getString(PreferencesKeys.userId)
    val userPhoneNumber = dataStore.getString(PreferencesKeys.userPhoneNumber)

    suspend fun userLoggedIn() = dataStore.saveBoolean(PreferencesKeys.userLoggedIn, true)
    suspend fun userLoggedOut() = dataStore.saveBoolean(PreferencesKeys.userLoggedIn, false)
    suspend fun saveUserId(userId: String) = dataStore.saveString(PreferencesKeys.userId, userId)
    suspend fun saveUserPhoneNo(userPhoneNo: String) =
        dataStore.saveString(PreferencesKeys.userPhoneNumber, userPhoneNo)

}