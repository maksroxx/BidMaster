package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.preferences.Preferences

class BidStateUseCase(private val preferences: Preferences) {
    operator fun invoke() : Boolean {
        return preferences.getBidState()
    }
}