package com.roxx.bidmaster

import android.util.Log
import com.google.gson.Gson
import com.roxx.bidmaster.data.network.api.BidApi
import com.roxx.bidmaster.domain.model.UserResponse
import com.roxx.bidmaster.data.repository.BidRepositoryImpl
import com.roxx.bidmaster.domain.model.Bid
import com.roxx.bidmaster.domain.model.BidId
import com.roxx.bidmaster.domain.model.BidResponse
import com.roxx.bidmaster.domain.model.Money
import com.roxx.bidmaster.domain.model.Result
import com.roxx.bidmaster.domain.model.User
import com.roxx.bidmaster.domain.model.UserRequest
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTests {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var bidApi: BidApi
    private lateinit var bidRepository: BidRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        bidApi = retrofit.create(BidApi::class.java)
        bidRepository = BidRepositoryImpl(bidApi)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // create user
    @Test
    fun `test createUser failure due to server error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = bidRepository.createUser(UserRequest("testUser", "testPassword"))

        assertTrue(result is Result.Error)
        assertEquals("Internal server error", result.message)
    }

    @Test
    fun `test createUser failure due to bad request`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        val result = bidRepository.createUser(UserRequest("testUser", "testPassword"))

        assertTrue(result is Result.Error)
        assertEquals("Bad request", result.message)
    }

    @Test
    fun `test createUser failure due to unauthorized`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("Unauthorized")
        )

        val result = bidRepository.createUser(UserRequest("testUser", "testPassword"))

        assertTrue(result is Result.Error)
        assertEquals("Unauthorized", result.message)
    }

    @Test
    fun `test createUser success`() = runBlocking {
        val userResponse = UserResponse(token = "test_token")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(userResponse))
        )

        val result = bidRepository.createUser(UserRequest("testUser", "testPassword"))

        assertTrue(result is Result.Success)
        assertEquals("test_token", (result as Result.Success).data?.token)
    }

    // login user
    @Test
    fun `test loginUser success`() = runBlocking {
        val userResponse = UserResponse(token = "test_token")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(userResponse))
        )

        val result = bidRepository.loginUser(UserRequest("testUser", "testPassword"))

        assertTrue(result is Result.Success)
        assertEquals("test_token", (result as Result.Success).data?.token)
    }

    // get top users
    @Test
    fun `test getTopUsers success`() = runBlocking {
        val users =
            listOf(User(username = "user1", balance = 100), User(username = "user2", balance = 200))
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(users))
        )

        val result = bidRepository.getTopUsers()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data?.size)
    }

    @Test
    fun `test getTopUsers returns empty list`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("[]")
        )

        val result = bidRepository.getTopUsers()

        assertTrue(result is Result.Success)
        assertEquals(0, (result as Result.Success).data?.size)
    }

    @Test
    fun `test getTopUsers failure due to internal server error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = bidRepository.getTopUsers()

        assertTrue(result is Result.Error)
        assertEquals("Internal server error", result.message)
    }

    // delete bid
    @Test
    fun `test deleteBid success`() = runBlocking {
        val moneyResponse = Money(amount = 100)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(moneyResponse))
        )

        val result = bidRepository.deleteBid(1)

        assertTrue(result is Result.Success)
        assertEquals(100, (result as Result.Success).data?.amount)
    }

    @Test
    fun `test deleteBid failure due to not found`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
        )

        val result = bidRepository.deleteBid(999)

        assertTrue(result is Result.Error)
        assertEquals("Resource not found", result.message)
    }

    @Test
    fun `test deleteBid failure due to internal server error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = bidRepository.deleteBid(1)

        assertTrue(result is Result.Error)
        assertEquals("Internal server error", result.message)
    }

    // validate token
    @Test
    fun `test validateToken success`() = runBlocking {
        val userResponse = UserResponse(token = "test_token")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(userResponse))
        )

        val result = bidRepository.validateToken()

        assertTrue(result is Result.Success)
        assertEquals("test_token", (result as Result.Success).data)
    }

    @Test
    fun `test validateToken failure due to invalid token`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("Invalid token")
        )

        val result = bidRepository.validateToken()

        assertTrue(result is Result.Error)
        assertEquals("Invalid token", result.message)
    }

    @Test
    fun `test validateToken failure due to internal server error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = bidRepository.validateToken()

        assertTrue(result is Result.Error)
        assertEquals("Internal server error", result.message)
    }

    // make bid
    @Test
    fun `test makeBid success`() = runBlocking {
        val bidResponse = BidResponse(34, 100)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(bidResponse))
        )

        val result = bidRepository.makeBid(Money(amount = 50))

        assertTrue(result is Result.Success)
        assertEquals(100, (result as Result.Success).data?.balance)
    }

    @Test
    fun `test makeBid failure due to insufficient funds`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        val result = bidRepository.makeBid(Money(amount = 1000))

        assertTrue(result is Result.Error)
        assertEquals("Bad request", result.message)
    }

    // get my information
    @Test
    fun `test getMyInformation success`() = runBlocking {
        val user = User(username = "testUser", balance = 100)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(user))
        )

        val result = bidRepository.getMyInformation()

        assertTrue(result is Result.Success)
        assertEquals("testUser", (result as Result.Success).data?.username)
    }

    @Test
    fun `test getMyInformation failure due to unauthorized`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("Unauthorized")
        )

        val result = bidRepository.getMyInformation()

        assertTrue(result is Result.Error)
        assertEquals("Unauthorized", result.message)
    }

    // my bids
    @Test
    fun `test getMyBids success`() = runBlocking {
        val bids = listOf(
            Bid(
                id = 1,
                amount = 50,
                createdAt = 1700002,
                profit = 1000,
                status = "COMPLETED"
            ),
            Bid(
                id = 3,
                amount = 100,
                createdAt = 1701231,
                profit = -10000,
                status = "COMPLETED"
            )
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(bids))
        )

        val result = bidRepository.getMyBids()

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data?.size)
    }

    @Test
    fun `test getMyBids failure due to internal server error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = bidRepository.getMyBids()

        assertTrue(result is Result.Error)
        assertEquals("Internal server error", result.message)
    }

    // last bid
    @Test
    fun `test getLatBid success`() = runBlocking {
        val bidId = BidId(lastBidId = -2)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(bidId))
        )

        val result = bidRepository.getLatBidId()

        assertTrue(result is Result.Success)
        assertEquals(-2, (result as Result.Success).data?.lastBidId)
    }
}