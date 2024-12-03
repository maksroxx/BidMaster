package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.repository.BidRepository

class GetMyInformationUseCase(
    private val bidRepository: BidRepository
) {
    suspend operator fun invoke(): Result<User> {
        return bidRepository.getMyInformation()
    }
}