package com.example.trans.network.Enums

enum class RequestStatus {
    REQ_NOT_STARTED,
    REQ_PROGRESS,
    REQ_SUCCESS,
    REQ_ERROR
}

enum class RegStatus(val status: Int) {
    UN_REG(0),
    FIREBASE_REG(1),
    REG(2)
}