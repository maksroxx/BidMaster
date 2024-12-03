package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository

class DeleteBidUseCase(
    private val repository: BidRepository,
    private val preferences: Preferences
) {
    suspend operator fun invoke(): Result<Money> {
        val bidId = preferences.getBidId()
        return when(val result = repository.deleteBid(bidId)) {
            is Result.Success -> {
                preferences.setBidState(false)
                result
            }
            is Result.Error -> {
                result
            }
        }
    }
}