package com.example.trans.utillity

import android.content.Context
import com.example.trans.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CommonFunctionsWithContext @Inject constructor(private val context: Context) {

    fun getAppVersion(): Int {
        return BuildConfig.VERSION_CODE
    }

}