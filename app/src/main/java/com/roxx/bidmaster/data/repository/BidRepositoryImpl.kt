package com.roxx.bidmaster.data.repository

import android.util.Log
import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.data.network.api.BidApi
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.data.network.model.UserResponse
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
            val bidResponse = bidApi.makeBid(money = money)
            Result.Success(bidResponse)
        }
    }

    override suspend fun getMyBids(): Result<List<Bid>> {
        return handleApiCall {
            val bids = bidApi.getMyBids()
            Result.Success(bids)
        }
    }

    override suspend fun getLatBidId(): Result<Int> {
        return handleApiCall {
            val bidId = bidApi.getLatBid()
            Result.Success(bidId.lastBidId)
        }
    }

    override suspend fun deleteBid(bidId: Int): Result<Money> {
        return handleApiCall {
            val money = bidApi.deleteBid(bidId = bidId)
            Result.Success(money)
        }
    }

    override suspend fun validateToken(): Result<String> {
        return handleApiCall {
            val result = bidApi.validateToken()
            Result.Success(result.token)
        }
    }
}