package com.example.trans.network.responses

sealed class ResultRes<out T> {
    data class Success<out T>(val data: T) : ResultRes<T>()
    data class Error(val message: String) : ResultRes<Nothing>()
    object Loading : ResultRes<Nothing>()
}