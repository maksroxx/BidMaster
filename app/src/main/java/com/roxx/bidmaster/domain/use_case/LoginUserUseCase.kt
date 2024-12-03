package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository

class LoginUserUseCase(
    private val bidRepository: BidRepository,
    private val preferences: Preferences
) {
    suspend operator fun invoke(userRequest: UserRequest): Result<String> {
        return when(val result = bidRepository.loginUser(userRequest)) {
            is Result.Success -> {
                result.data?.let { preferences.saveToken(it.token) }
                Result.Success("Success")
            }
            is Result.Error -> {
                Result.Error(message = result.message ?: "Unknown error")
            }
        }
    }
}