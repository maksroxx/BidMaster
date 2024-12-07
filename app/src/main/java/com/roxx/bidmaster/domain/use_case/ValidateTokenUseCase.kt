package com.roxx.bidmaster.domain.use_case

import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.preferences.Preferences
import com.roxx.bidmaster.domain.repository.BidRepository

class ValidateTokenUseCase(
    private val repository: BidRepository,
    private val preferences: Preferences
) {
    suspend operator fun invoke(): Result<String> {
        return if (preferences.getToken() != null) {
            val result = repository.validateToken()
            when (result) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> Result.Error(result.message ?: "Token Invalid")
            }
        } else {
            Result.Error("No token")
        }
    }
}