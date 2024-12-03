package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.repository.BidRepository

class GetBidsUseCase(private val repository: BidRepository) {
    suspend operator fun invoke(): Result<List<Bid>> {
        return repository.getMyBids()
    }
}