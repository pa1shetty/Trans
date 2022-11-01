package com.example.trans.network.responses


data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)

class Quote
