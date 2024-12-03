package com.roxx.bidmaster.data.model

data class Bid(
    val id: Int,
    val amount: Int,
    val createdAt: Int,
    val status: String,
    val profit: Int
)