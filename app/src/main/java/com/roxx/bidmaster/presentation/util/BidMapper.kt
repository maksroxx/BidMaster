package com.roxx.bidmaster.presentation.util

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.presentation.screens.profile.BidUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Bid.toBidUi(): BidUi {
    return BidUi(
        id = id,
        amount = amount,
        createdAt = createdAt.toDataString(),
        status = status,
        profit = profit
    )
}

fun Int.toDataString(): String {
    val date = Date(this.toLong() * 1000)
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(date)
}