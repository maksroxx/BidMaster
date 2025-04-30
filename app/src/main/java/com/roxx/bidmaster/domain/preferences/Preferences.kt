package com.roxx.bidmaster.domain.preferences

interface Preferences {

    // token
    fun saveToken(key: String)
    fun getToken(): String?

    // bidState
    fun setBidState(state: Boolean)
    fun getBidState(): Boolean

    // bidId
    fun saveBidId(bidId: Int)
    fun getBidId(): Int

    // search
    fun saveSearchHistory(query: String)
    fun getSearchHistory(): List<String>
    fun clearSearchHistory()

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_BID = "bid"
        const val KEY_STATE = "state"
        const val KEY_SEARCH_HISTORY = "key_search_history"
    }
}