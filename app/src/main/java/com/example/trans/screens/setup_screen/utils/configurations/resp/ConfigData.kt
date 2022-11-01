package com.example.trans.screens.setup_screen.utils.configurations.resp

data class ConfigData(
    val login_mandatory: Int,
    val max_active_tickets: Int,
    val max_otp_length: Int,
    val max_passenger_count: Int,
    val max_phone_number: Int,
    val max_recent_tickets: Int,
    val max_suggestion_routes: Int,
    val qr_update_seconds: Int,
    val resend_wait_second: Int,
    val ticket_prior_days: Int
)