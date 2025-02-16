package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.SearchUser
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.repository.BidRepository

class SearchUserUseCase(
    private val bidRepository: BidRepository
) {
    suspend operator fun invoke(username: String): Result<User> {
        return bidRepository.searchUser(SearchUser(username))
    }
}