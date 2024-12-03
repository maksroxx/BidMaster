package com.roxx.bidmaster.data.preferences

import android.content.SharedPreferences
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.preferences.Preferences.Companion.KEY_BALANCE
import com.roxx.bidmaster.domain.preferences.Preferences.Companion.KEY_BID
import com.roxx.bidmaster.domain.preferences.Preferences.Companion.KEY_STATE
import com.roxx.bidmaster.domain.preferences.Preferences.Companion.KEY_TOKEN
import com.roxx.bidmaster.domain.preferences.Preferences.Companion.KEY_USERNAME

class PreferencesImpl(
    private val sharedPreferences: SharedPreferences
): Preferences {
    override fun saveToken(key: String) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN, key)
            .apply()
    }

    override fun getToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, null)!!
    }

    // не нужно
    override fun saveBalance(balance: Int) {
        sharedPreferences.edit()
            .putInt(KEY_BALANCE, balance)
            .apply()
    }

    // не нужно
    override fun getBalance(): Int {
        return sharedPreferences.getInt(KEY_BALANCE, 0)
    }

    // не нужно
    override fun saveUsername(username: String) {
        sharedPreferences.edit()
            .putString(KEY_USERNAME, username)
            .apply()
    }

    // не нужно
    override fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "Bob")!!
    }

    override fun setBidState(state: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_STATE, state)
            .apply()
    }

    override fun getBidState(): Boolean {
        return sharedPreferences.getBoolean(KEY_STATE, false)
    }

    override fun saveBidId(bidId: Int) {
        sharedPreferences.edit()
            .putInt(KEY_BID, bidId)
            .apply()
    }

    override fun getBidId(): Int {
        return sharedPreferences.getInt(KEY_BID, 0)
    }
}