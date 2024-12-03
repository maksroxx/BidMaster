package com.roxx.bidmaster.domain.preferences

interface Preferences {

    // token
    fun saveToken(key: String)
    fun getToken(): String

    // balance
    fun saveBalance(balance: Int)
    fun getBalance(): Int

    // username
    fun saveUsername(username: String)
    fun getUsername(): String

    // bidState
    fun setBidState(state: Boolean)
    fun getBidState(): Boolean

    // bidId
    fun saveBidId(bidId: Int)
    fun getBidId(): Int

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_BALANCE = "balance"
        const val KEY_BID = "bid"
        const val KEY_STATE = "state"
        const val KEY_USERNAME = "username"
    }
}