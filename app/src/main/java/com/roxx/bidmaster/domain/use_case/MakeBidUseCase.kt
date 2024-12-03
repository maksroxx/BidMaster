package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository

class MakeBidUseCase(
    private val repository: BidRepository,
    private val preferences: Preferences
) {
    suspend operator fun invoke(money: Money): Result<BidResponse> {
        return when(val result = repository.makeBid(money)) {
            is Result.Success -> {
                preferences.setBidState(true)
                result
            }
            is Result.Error -> {
                result
            }
        }
    }
}