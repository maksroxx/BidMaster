package com.roxx.bidmaster.data.network.api

import com.roxx.bidmaster.domain.model.BidId
import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.SearchUser
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import com.roxx.bidmaster.domain.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
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
    suspend fun getMyInformation(
        @Header("Requires-Auth") requiresAuth: String = "true"
    ): User

    @GET("/me/bids")
    suspend fun getMyBids(
        @Header("Requires-Auth") requiresAuth: String = "true"
    ): List<Bid>

    @POST("/me/bid")
    suspend fun makeBid(
        @Header("Requires-Auth") requiresAuth: String = "true",
        @Body money: Money
    ): BidResponse

    @GET("/me/bid/last")
    suspend fun getLatBid(
        @Header("Requires-Auth") requiresAuth: String = "true"
    ): BidId

    @GET("/protected")
    suspend fun validateToken(
        @Header("Requires-Auth") requiresAuth: String = "true"
    ): UserResponse

    @DELETE("/me/bids/{id}")
    suspend fun deleteBid(
        @Header("Requires-Auth") requiresAuth: String = "true",
        @Path("id") bidId: Int
    ): Money

    @POST("/search")
    suspend fun searchUser(
        @Header("Requires-Auth") requiresAuth: String = "true",
        @Body searchUser: SearchUser
    ): User

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }
}