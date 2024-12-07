package com.roxx.bidmaster.domain.repository

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.data.network.model.UserResponse
import com.roxx.bidmaster.domain.model.Result

interface BidRepository {
    suspend fun createUser(userRequest: UserRequest): Result<UserResponse>
    suspend fun loginUser(userRequest: UserRequest): Result<UserResponse>
    suspend fun getTopUsers(): Result<List<User>>
    suspend fun getMyInformation(): Result<User>
    suspend fun makeBid(money: Money): Result<BidResponse>
    suspend fun getMyBids(): Result<List<Bid>>
    suspend fun getLatBidId(): Result<Int>
    suspend fun deleteBid(bidId: Int): Result<Money>
}