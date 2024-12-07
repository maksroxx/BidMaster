package com.roxx.bidmaster.domain.use_case

import android.util.Log
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository

class GetLastBidUseCase(
    private val repository: BidRepository,
    private val preferences: Preferences
) {
    suspend operator fun invoke() {
        when (val result = repository.getLatBidId()) {
            is Result.Error -> {

            }

            is Result.Success -> {
                result.data?.let {
                    if (it == -2) {
                        preferences.setBidState(false)
                    } else {
                        preferences.setBidState(true)
                        preferences.saveBidId(it)
                        Log.d("Bid Id", it.toString())
                    }
                }
            }
        }
    }
}