package com.example.trans.utillity.logger

import android.util.Log
import com.example.trans.BuildConfig
import kotlin.jvm.internal.Intrinsics



open class Logger(private val TAG:String="LOG_TAG") {
    var isLoggingEnabled: Boolean
        get() = Companion.isLoggingEnabled
        set(var1) {
            Companion.isLoggingEnabled = var1
        }

    open fun debug(message: Function0<*>) {
        Intrinsics.checkNotNullParameter(message, "message")
        if (Companion.isLoggingEnabled && BuildConfig.DEBUG) {
            Log.d(TAG, (message.invoke() as String?)!!)
        }
    }

    fun error(message: Function0<*>) {
        Intrinsics.checkNotNullParameter(message, "message")
        if (Companion.isLoggingEnabled && BuildConfig.DEBUG) {
            Log.e(TAG, (message.invoke() as String?)!!)
        }
    }

    fun error(message: Function0<*>, throwable: Throwable) {
        Intrinsics.checkNotNullParameter(message, "message")
        Intrinsics.checkNotNullParameter(throwable, "throwable")
        if (Companion.isLoggingEnabled && BuildConfig.DEBUG) {
            Log.e(TAG, message.invoke() as String?, throwable)
        }
    }

    companion object {
        val INSTANCE = Logger()
        private var isLoggingEnabled = true
    }
}