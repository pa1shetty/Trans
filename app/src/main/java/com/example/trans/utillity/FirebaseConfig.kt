package com.example.trans.utillity

import com.example.trans.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseConfig @Inject constructor() {
    private var remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        initFirebaseRemoteConfig()
    }

    private fun initFirebaseRemoteConfig() {
        remoteConfig.apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
        }

    }

    fun getMinAppVersion() = remoteConfig.getString("min_version_of_app")

    fun fetchAndActivate() = remoteConfig.fetchAndActivate()

}