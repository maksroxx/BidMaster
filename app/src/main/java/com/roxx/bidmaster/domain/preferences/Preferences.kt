package com.roxx.bidmaster.domain.preferences

interface Preferences {

    // token
    fun saveToken(key: String)
    fun getToken(): String

    // balance
    fun saveBalance(balance: Int)
    fun getBalance(): Int

    // bidId
    fun saveBidId(bidId: Int)
    fun getBidId(): Int

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_BALANCE = "balance"
        const val KEY_BID = "bid"
    }
}