package com.example.trans.utillity.logger


class NetworkLogger private constructor(TAG:String="NETWORK_TAG"):Logger(TAG)
{
    companion object {
        val INSTANCE = NetworkLogger()
    }
}

