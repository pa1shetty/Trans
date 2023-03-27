package com.example.trans.network.responses


data class UserData(
    val regStatus: Int = 0,
    val user: UserDetails = UserDetails()
)