package com.roxx.bidmaster.domain.use_case

import android.util.Log
import com.roxx.bidmaster.domain.preferences.Preferences

class BidStateUseCase(private val preferences: Preferences) {
    operator fun invoke() : Boolean {
        val state = preferences.getBidState()
        Log.d("Bid State Use Case", state.toString())
        return state
    }
}