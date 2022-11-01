package com.example.trans.utillity

import com.example.trans.BuildConfig
import com.example.trans.utillity.AppUpdate.UpdateType.*
import com.example.trans.utillity.firebase.FireBaseConfigRepository
import javax.inject.Inject


class AppUpdate @Inject constructor(private val fireBaseConfigRepository: FireBaseConfigRepository) {
    fun isUpdateNeeded() =
        if (currentAppVersion < fireBaseConfigRepository.latestAppVersion)
            if (currentAppVersion < fireBaseConfigRepository.minAppVersion)
                MANDATORY_UPDATE
            else OPTIONAL_UPDATE else
            NO_UPDATE


    enum class UpdateType {
        NO_UPDATE,
        OPTIONAL_UPDATE,
        MANDATORY_UPDATE
    }

    private val currentAppVersion = BuildConfig.VERSION_CODE
}