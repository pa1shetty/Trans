package com.example.trans.utillity.firebase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireBaseConfigRepository @Inject constructor() : FirebaseConfig() {
    val minAppVersion = remoteConfig.getLong("min_version_of_app")
    val latestAppVersion = remoteConfig.getLong("latest_version_of_app")
    val isLoginMandatory = remoteConfig.getBoolean("is_login_mandatory")

}