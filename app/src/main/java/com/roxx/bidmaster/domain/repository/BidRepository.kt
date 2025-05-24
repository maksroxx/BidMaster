package com.roxx.bidmaster.domain.repository

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.domain.model.UserResponse
import com.roxx.bidmaster.domain.model.BidId
import com.roxx.bidmaster.domain.model.DailyItem
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.SearchUser

interface BidRepository {
    suspend fun createUser(userRequest: UserRequest): Result<UserResponse>
    suspend fun loginUser(userRequest: UserRequest): Result<UserResponse>
    suspend fun getTopUsers(): Result<List<User>>
    suspend fun getMyInformation(): Result<User>
    suspend fun makeBid(money: Money): Result<BidResponse>
    suspend fun getMyBids(): Result<List<Bid>>
    suspend fun getLatBidId(): Result<BidId>
    suspend fun deleteBid(bidId: Int): Result<Money>
    suspend fun validateToken(): Result<String>
    suspend fun searchUser(searchUser: SearchUser): Result<User>
    suspend fun getDailyItem(): Result<DailyItem>
}