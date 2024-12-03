package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.repository.BidRepository

class GetTopUsersUseCase(
    private val bidRepository: BidRepository
) {
    suspend fun invoke(): Result<List<User>> {
        return bidRepository.getTopUsers()
    }
}