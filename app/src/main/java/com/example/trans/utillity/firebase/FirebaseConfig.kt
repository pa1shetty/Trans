package com.example.trans.utillity.firebase

import com.example.trans.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class FirebaseConfig @Inject constructor() {
    protected val remoteConfig: FirebaseRemoteConfig by lazy { FirebaseRemoteConfig.getInstance() }

    init {
        remoteConfig.apply {
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(0)
                    .build()
            )
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }

    fun fetchAndActivate() = remoteConfig.fetchAndActivate()

}