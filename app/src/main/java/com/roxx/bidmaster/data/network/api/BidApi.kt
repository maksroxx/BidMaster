package com.roxx.bidmaster.data.network.api

import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.data.network.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BidApi {
    @POST("/users")
    suspend fun createUser(@Body userRequest: UserRequest): UserResponse

    @POST("/login")
    suspend fun loginUser(@Body userRequest: UserRequest): UserResponse

    @GET("/top")
    suspend fun getTopUsers(): List<User>

    @GET("/me")
    suspend fun getMyInformation(): User

    @GET("/me/bids")
    suspend fun getMyBids(): List<Bid>

    @POST("/me/bid")
    suspend fun makeBid(@Body money: Money): BidResponse

    @DELETE("/me/bids/{id}")
    suspend fun deleteBid(@Path("id") bidId: Int): Money

    companion object {
        const val BASE_URL = "http://192.168.1.105:8080"
    }
}