package com.example.trans.network.responses


data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)

class User
