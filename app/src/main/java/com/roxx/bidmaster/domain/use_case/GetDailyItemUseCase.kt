package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.DailyItem
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.repository.BidRepository

class GetDailyItemUseCase(private val repository: BidRepository) {
    suspend operator fun invoke(): Result<DailyItem> {
        return repository.getDailyItem()
    }
}