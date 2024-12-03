package com.roxx.bidmaster.data.repository

import android.util.Log
import com.roxx.bidmaster.data.model.Bid
import com.roxx.bidmaster.data.model.BidResponse
import com.roxx.bidmaster.data.model.Money
import com.roxx.bidmaster.data.network.api.BidApi
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.data.model.UserRequest
import com.roxx.bidmaster.data.model.UserResponse
import com.roxx.bidmaster.data.network.ApiErrorHandler.handleApiCall
import com.roxx.bidmaster.domain.repository.BidRepository
import com.roxx.bidmaster.domain.model.Result

class BidRepositoryImpl(private val bidApi: BidApi) : BidRepository {
    override suspend fun createUser(userRequest: UserRequest): Result<UserResponse> {
        return handleApiCall {
            val userResponse = bidApi.createUser(userRequest)
            Result.Success(userResponse)
        }
    }

    override suspend fun loginUser(userRequest: UserRequest): Result<UserResponse> {
        return handleApiCall {
            val userResponse = bidApi.loginUser(userRequest)
            Result.Success(userResponse)
        }
    }

    override suspend fun getTopUsers(): Result<List<User>> {
        return handleApiCall {
            val users = bidApi.getTopUsers()
            Result.Success(users)
        }
    }

    override suspend fun getMyInformation(): Result<User> {
        return handleApiCall {
            val user = bidApi.getMyInformation()
            Result.Success(user)
        }
    }

    override suspend fun makeBid(money: Money): Result<BidResponse> {
        return handleApiCall {
            val bidResponse = bidApi.makeBid(money)
            Result.Success(bidResponse)
        }
    }

    override suspend fun getMyBids(): Result<List<Bid>> {
        return handleApiCall {
            val bids = bidApi.getMyBids()
            Result.Success(bids)
        }
    }

    override suspend fun deleteBid(bidId: Int): Result<Money> {
        return handleApiCall {
            val money = bidApi.deleteBid(bidId)
            Result.Success(money)
        }
    }
}